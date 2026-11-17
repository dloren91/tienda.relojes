package com.lorenzo.tienda.relojes.controladoresREST;

import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.responseData.ResumenPedido;
import com.lorenzo.tienda.relojes.servicios.ServicioPedidos;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("RESTpedido/")
public class PedidosREST {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("procesarPaso1")
    public String paso1(String nombre, String direccion, String provincia,String codigoPostal,String telefono,String observaciones, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso1(nombre,direccion,provincia,codigoPostal ,telefono,observaciones , u.getId());
        return "ok";
    }

    @RequestMapping("procesarPaso2")
    public String paso2(String titular, String numero, String tarjeta, boolean aPlazos, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso2(titular, numero, tarjeta, aPlazos, u.getId());
        return "ok";
    }

    @RequestMapping("procesarPreguntas")
    public ResumenPedido procesarPreguntas(String resPregunta1, String resPregunta2, HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.procesarPaso3(resPregunta1, resPregunta2, u.getId());
        return servicioPedidos.obtenerResumenPedido(u.getId());
    }

    @RequestMapping("procesarPaso3")
    public String procesarPaso3(HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
        servicioPedidos.confirmarPedido(u.getId());
        return "ok";
    }

}
