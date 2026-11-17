package com.lorenzo.tienda.relojes.controllers;

import com.lorenzo.tienda.relojes.model.Pedido;
import com.lorenzo.tienda.relojes.servicios.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/")
public class ControladorPedidos {

    @Autowired
    private ServicioPedidos servicioPedidos;

    @RequestMapping("gestionarPedidos")
    public String gestionarPedidos(Model model){
        model.addAttribute("pedidos", servicioPedidos.obtenerPedidios());
        return "admin/gestionar/gestionar_pedidos";
    }

    @RequestMapping("verDetallesPedido")
    public String verDetallesPedido(@RequestParam("id") Long id, Model model){
        Pedido p = servicioPedidos.obtenerPedidosPorId(id);
        model.addAttribute("pedido", p);
        return "admin/pedido_detalles";
    }
}
