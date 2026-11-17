package com.lorenzo.tienda.relojes.serviciosImpl;

import com.lorenzo.tienda.relojes.constantesPedidos.EstadosPedido;
import com.lorenzo.tienda.relojes.constantesSQL.ConstantesSQL;
import com.lorenzo.tienda.relojes.model.*;
import com.lorenzo.tienda.relojes.responseData.ResumenPedido;
import com.lorenzo.tienda.relojes.servicios.ServicioCarrito;
import com.lorenzo.tienda.relojes.servicios.ServicioPedidos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServicioPedidosImplJPA implements ServicioPedidos {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ServicioCarrito servicioCarrito;
    // operaciones con la parte publica

    @Override
    public List<Pedido> obtenerPedidios() {

        return entityManager.createQuery("select p from Pedido p order by p.id desc").getResultList();
    }

    @Override
    public Pedido obtenerPedidosPorId(long id) {
        return  entityManager.find(Pedido.class, id);
    }

    @Override
    public void actualizarEstadoPedido(long id, String estado) {
        Pedido p = entityManager.find(Pedido.class, id);
        p.setEstado(estado);
        entityManager.merge(p);

    }

    @Override
    public void procesarPaso1(String nombreCompleto, String direccion, String provincia,String codigoPostal,String telefono,String observaciones, long idUsuario) {
        //cada usuario podra tener como maximo un solo
        //pedido en estado "en proceso"
        //ese pedido es donde rellenaremos los datos de envio,
        //pago y demas, si hubiera
        //si el usuario finaliza el pedido, el estado del pedido
        //pasa a ser "completado"
        //puede haber tantos pedidos "completado" como se quiera
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setNombreCompleto(nombreCompleto);
        p.setDireccion(direccion);
        p.setProvincia(provincia);
        p.setCodigoPostal(codigoPostal);
        p.setTelefono(telefono);
        p.setObservaciones(observaciones);
        entityManager.merge(p);//actualiza o registra
    }

    @Override
    public void procesarPaso2(String titular, String numero, String tipoTarjeta, boolean aPlazos, long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setTitularTarjeta(titular);
        p.setNumeroTarjeta(numero);
        p.setTipoTarjeta(tipoTarjeta);
        p.setAPlazos(aPlazos);
        entityManager.merge(p);
    }

    @Override
    public void procesarPaso3(String resPregunta1, String resPregunta2, long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        p.setResPregunta1(resPregunta1);
        p.setResPregunta2(resPregunta2);
        entityManager.merge(p);
    }

    @Override
    public ResumenPedido obtenerResumenPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        ResumenPedido resumen = new ResumenPedido();
        resumen.setNombreCompleto(p.getNombreCompleto());
        resumen.setDireccion(p.getDireccion());
        resumen.setProvincia(p.getProvincia());
        resumen.setCodigoPostal(p.getCodigoPostal());      // nuevo
        resumen.setTelefono(p.getTelefono());              // nuevo
        resumen.setObservaciones(p.getObservaciones());    // nuevo
        resumen.setTipoTarjeta(p.getTipoTarjeta());
        resumen.setTitularTarjeta(p.getTitularTarjeta());
        resumen.setNumeroTarjeta(p.getNumeroTarjeta());
        resumen.setaPlazos(p.isAPlazos());                 // nuevo
        resumen.setRelojes(
                servicioCarrito.obtenerProductosCarrito(idUsuario));
        return resumen;
    }

    @Override
    public void confirmarPedido(long idUsuario) {
        Pedido p = obtenerPedidoActual(idUsuario);
        Carrito c = entityManager.createQuery(
                        "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        //pasar los productos del carrito al pedido
        if( c != null && c.getProductosCarrito().size() > 0){
            for(ProductoCarrito pc : c.getProductosCarrito()){
                ProductoPedido pp = new ProductoPedido();
                pp.setCantidad(pc.getCantidad());
                pp.setReloj(pc.getReloj());
                p.getProductosPedido().add(pp);
                pp.setPedido(p);
                entityManager.persist(pp);
            }
        }
        //borrar los productos del carrito del usuario
        entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO).setParameter("carrito_id", c.getId()).executeUpdate();
        p.setEstado(EstadosPedido.Completado.toString());
        entityManager.merge(p);
    }



    private Pedido obtenerPedidoActual(long idUsuario) {
        //vamos a pedir del usuario actual todos los pedidos "en proceso"
        Query consulta =
                entityManager.createQuery(
                                "select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id"
                        ).setParameter("estado", EstadosPedido.EnProceso.name())
                        .setParameter("usuario_id", idUsuario);
        List<Pedido> resultadoConsulta = consulta.getResultList();
        if(resultadoConsulta.size()==1){
            return resultadoConsulta.get(0);
        }
        //si el metodo sigue para adelante hay que crear un nuevo
        //pedido con el estado en proceso
        Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);
        Pedido pedidoEnProceso = new Pedido();
        pedidoEnProceso.setUsuario(uBaseDatos);
        pedidoEnProceso.setEstado(EstadosPedido.EnProceso.name());
        return pedidoEnProceso;
    }
}










