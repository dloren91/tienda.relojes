package com.lorenzo.tienda.relojes.serviciosImpl;

import com.lorenzo.tienda.relojes.constantesSQL.ConstantesSQL;
import com.lorenzo.tienda.relojes.model.Carrito;
import com.lorenzo.tienda.relojes.model.ProductoCarrito;
import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioCarrito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioCarritoImplJPA implements ServicioCarrito {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void agregarProducto(long idUsuario, long idProducto, int cantidad) {
        Usuario uBaseDatos =
                entityManager.find(Usuario.class, idUsuario);
        Carrito c = buscarCarrito(idUsuario);
        if (c == null) {
            c = new Carrito();
            c.setUsuario(uBaseDatos);
            entityManager.persist(c);
        }
        //ver si el producto que se quiere agregar ya esta en el carrito
        //si lo esta, hay que incrementar su cantidad
        boolean productoEnCarrito = false;
        for(ProductoCarrito pc : c.getProductosCarrito()){
            if(pc.getReloj().getId() == idProducto){
                //el producto ya esta en el carrito
                productoEnCarrito = true;
                pc.setCantidad(pc.getCantidad() + cantidad);
                entityManager.merge(pc);
            }
        }
        //si el producto no esta en el carrito, creamos un nuevo ProductoCarrito
        if( ! productoEnCarrito ){
            ProductoCarrito pc = new ProductoCarrito();
            pc.setCarrito(c);
            pc.setReloj( entityManager.find(Reloj.class, idProducto) );
            pc.setCantidad(cantidad);
            entityManager.persist(pc);
        }
    }

    @Override
    public List<Map<String, Object>> obtenerProductosCarrito(long idUsuario) {
        //sacar los productos del carrito a partir de la id de usuario
        // OBTENER EL CARRITO ASI AHORA MISMO NO ES FIABLE:
//        Usuario u = entityManager.find(Usuario.class, idUsuario)
//         Carrito c = u.getCarrito();
        // ESTO MEJOR:
        Carrito c = buscarCarrito(idUsuario);
        if (c == null) {
            return List.of();
        }

        Query query = entityManager.createNativeQuery(
                ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO_JSON
        );
        query.setParameter("carrito_id", c.getId());
        NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
        nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return nativeQuery.getResultList();
    }

    private Carrito buscarCarrito(long idUsuario) {
        List<Carrito> carritos = entityManager.createQuery(
                        "SELECT c FROM Carrito c WHERE c.usuario.id = :idUsuario", Carrito.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
        return carritos.isEmpty() ? null : carritos.get(0);
    }
}
