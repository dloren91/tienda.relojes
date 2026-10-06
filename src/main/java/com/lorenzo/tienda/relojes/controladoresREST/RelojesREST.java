package com.lorenzo.tienda.relojes.controladoresREST;

import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.servicios.ServicioReloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("RESTrelojes/")
public class RelojesREST {

    @Autowired
    private ServicioReloj servicioReloj;

    @RequestMapping("listar")
    public List<Reloj> listar(){
        return servicioReloj.obtenerReloj();
    }

    @RequestMapping("obtener-reloj")
    public Reloj obtenerReloj(@RequestParam("id") Long id){
        return servicioReloj.obtenerRelojPorId(id);
    }
}