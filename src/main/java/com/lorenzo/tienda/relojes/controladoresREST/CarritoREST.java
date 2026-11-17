package com.lorenzo.tienda.relojes.controladoresREST;

import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioCarrito;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("RESTcarrito/")
public class CarritoREST {

    @Autowired
    private ServicioCarrito servicioCarrito;

    @RequestMapping("agregarProducto")
    public String agregarProducto(@RequestParam("id") String id,
                                  @RequestParam("cantidad") String cantidad,
                                  HttpServletRequest request){
        String respuesta = "";
        //comprobar que el usuario esta identificado...

        //la informacion del usuario esta metida en una variable de sesion -> consultar usuarios REST UsuariosREST/identificarUsuario
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        respuesta = "producto agregado (consultar h2): id producto: "+ id +" cantidad: "+ cantidad+ " al usuario de id: "+ u.getId();
        servicioCarrito.agregarProducto(
                u.getId(),
                Long.parseLong(id),
                Integer.parseInt(cantidad)
        );
        return respuesta;
    }
    @RequestMapping("obtenerProductos")
    public List<Map<String,Object>> obtenerProductos(HttpServletRequest request){
        return servicioCarrito.obtenerProductosCarrito(
                ((Usuario)request.getSession().
                        getAttribute("usuario_identificado")).getId()
        );
    }

}
