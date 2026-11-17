package com.lorenzo.tienda.relojes.serviciosImpl;

import com.lorenzo.tienda.relojes.model.*;
import com.lorenzo.tienda.relojes.servicios.ServicioPedidos;
import com.lorenzo.tienda.relojes.servicios.ServicioSetUp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Transactional
public class ServiciosSetUpImplJPA implements ServicioSetUp {

    @PersistenceContext
    private EntityManager entityManager;

    //en el caso de los pedidos arovechamos
    // y testeamos el servicio pedidos
    @Autowired
    private ServicioPedidos servicioPedidos;



    @Override
    public void prepararRegistrosIniciales() {
        //preparar unos servicios si la bbdd esta vacia
        Configuracion configuracion = null;
        //la idea presentada se basa que va haber un solo registro en la tabla de Configuracion, ese registro nos indicara
        // si la bbdd ya tiene sus regsitros iniciales como por ejem: productos categorias pedidos etc.
        //en sql seria como: select * from configuracon as c
        try {
            configuracion = (Configuracion)entityManager.createQuery("select c from Configuracion c").getSingleResult();
        }catch (NoResultException e){
            System.out.println("No se encontro un unico registro en la tabla de configuracion");
        }
        if(configuracion == null || ! configuracion.isRegistrosIniciales()){
            //hago todos los registros iniciales
            //categorias
            // --- Categorías de Relojes ---
            Categoria cDeportivo = new Categoria("DEPORTIVO", "Relojes Deportivos");
            Categoria cLujo = new Categoria("LUJO", "Relojes de Lujo");
            Categoria cSmartwatch = new Categoria("SMART", "Smartwatches");
            Categoria cClasico = new Categoria("CLASICO", "Relojes Clásicos");

            entityManager.persist(cDeportivo);
            entityManager.persist(cLujo);
            entityManager.persist(cSmartwatch);
            entityManager.persist(cClasico);

            //productos
            //el primer producto tendra id 1
            Reloj rSeiko = new Reloj("Seiko Prospex", 450.00, "Seiko", "Acero Inoxidable", 200, true);
            rSeiko.setCategoria(cDeportivo);
            rSeiko.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/seiko.png"));


            Reloj rRolex = new Reloj("Submariner", 9500.00, "Rolex", "Oro Blanco", 300, true);
            rRolex.setCategoria(cLujo);
            rRolex.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/rolex.png"));

            Reloj rCasio = new Reloj("Casio Vintage", 35.50, "Casio", "Resina", 30, false);
            rCasio.setCategoria(cClasico);
            rCasio.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/casio.png"));


            Reloj rApple = new Reloj("Apple Watch Ultra", 899.00, "Apple", "Titanio", 100, false);
            rApple.setCategoria(cSmartwatch);
            rApple.setImagenPortada(leerImagenInicial("http://localhost:8080/imagenes_productos_iniciales/apple.png"));


            entityManager.persist(rSeiko);
            entityManager.persist(rRolex);
            entityManager.persist(rCasio);
            entityManager.persist(rApple);








            //vamos a crear algun usuario
            Usuario u1 = new Usuario();
            u1.setNombre("Lorenzo");
            u1.setPass("123");
            u1.setEmail("lorenzo@123");
            entityManager.persist(u1);

            //vamos a preparar un carrito
            Carrito c1 = new Carrito();
            c1.setUsuario(u1);
            entityManager.persist(c1);

            ProductoCarrito pc1 = new ProductoCarrito();
            pc1.setCarrito(c1);
            pc1.setReloj(rRolex);
            pc1.setCantidad(2);
            c1.getProductosCarrito().add(pc1);
            entityManager.persist(pc1);

            ProductoCarrito pc2 = new ProductoCarrito();
            pc2.setCarrito(c1);
            pc2.setReloj(rApple);
            pc2.setCantidad(3);
            c1.getProductosCarrito().add(pc2);
            entityManager.persist(pc2);

            //vamos tambien a registrar un pedido
            // a partir del carrito
            servicioPedidos.procesarPaso1("Paco Pepe","Valdencinas", "Madrid","28035","6161114269","El portero se llama Juan" ,u1.getId());
            servicioPedidos.procesarPaso2("Paco Pepe Pipi","123456789", "Visa", true, u1.getId());
            servicioPedidos.procesarPaso3("Reloj verde de Ben 10","15", u1.getId());
            servicioPedidos.confirmarPedido(u1.getId());
            //ya tenemmos un pedido


            String modelo = "modelo de prueba";
            double precio = 1;
            String material = "producto de prueba";
            String marca = "marca de prueba";
            for (int i = 0; i < 100 ; i++) {
                    Reloj rNuevo = new Reloj();
                    rNuevo.setModelo(modelo + i);
                    rNuevo.setPrecio(precio);
                    rNuevo.setMaterial(material + i);
                    rNuevo.setCategoria(cDeportivo);
                    rNuevo.setMarca(marca + i);
                    rNuevo.setAutomatico(true);
                    rNuevo.setResistenciaAgua(i+1);
                    rNuevo.setImagenPortada(leerImagenInicial(
                            "http://localhost:8080/imagenes_productos_iniciales/RelojDefault.png"
                    ));
                    entityManager.persist(rNuevo);
            }







            //una vez realizados los registros iniciales
            //guardo un registro en la tabla configuracion
            //indicando que toodo esta ok
            configuracion = new Configuracion();
            configuracion.setRegistrosIniciales(true);
            entityManager.persist(configuracion);



        }//end if

    }//end prepararRegistrosIniciales
    private byte[] leerImagenInicial(String ruta_imagen){
        byte[] info = null;
        try {
            URL url = new URL(ruta_imagen);
            info = IOUtils.toByteArray(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info;
    }

}//end class
