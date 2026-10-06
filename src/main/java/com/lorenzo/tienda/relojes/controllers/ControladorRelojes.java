package com.lorenzo.tienda.relojes.controllers;

import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.servicios.ServicioReloj;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/")
public class ControladorRelojes {

    @Autowired
    private ServicioReloj servicioReloj;

    @RequestMapping("gestionarRelojes")
    public String gestionarReloj(Model model){
        model.addAttribute("relojes",servicioReloj.obtenerReloj());

        return "admin/gestionar_productos";
    }
    @RequestMapping("registrarReloj")
    public String registrarReloj(Model model){
        Reloj nuevo = new Reloj();
        nuevo.setPrecio(1);
        model.addAttribute("nuevo",nuevo);
        return "admin/registro_producto";
    }

    @RequestMapping("guardarReloj")
    public String guardarReloj(@ModelAttribute("nuevo") @Valid Reloj nuevo, BindingResult resultadoValidacion, Model model){
        System.out.println("recibido para registrar: " + nuevo);
        if(resultadoValidacion.hasErrors()){
            //si el videojuego tiene errores mandamos al usuario a el formulario de nuevo
            return "admin/registro_producto";
        }
        servicioReloj.registrarReloj(nuevo);
        return "admin/registro_producto_ok";
    }

    @RequestMapping("borrarReloj")
    public String borrarReloj(@RequestParam("id")Long id, Model model){
        System.out.println("id recibe para borrar relojes de id: " + id);
        servicioReloj.borrarReloj(id);
        return gestionarReloj(model);
    }
    @RequestMapping("editarReloj")
    public String editarReloj(@RequestParam("id")Long id, Model model){
        model.addAttribute("relojEditar",servicioReloj.obtenerRelojPorId(id));
        return "admin/editar_producto";
    }

    //el metodo va a tender la ruta guardarCambiosVideojuego a esa ruta se llega al hacer click en el Guardar Cambios del form edicion
    @RequestMapping("guardarCambiosReloj")
    public String guardarCambiosReloj (
            //es el th:object del form
            @ModelAttribute("nuevo")
            //para validar con las reglas puestas con el @ en Videojuego.java
            @Valid
            Reloj relojEditar,//objeto que contiene lo incluido en el form
            BindingResult resultadoValidacion,
            Model model) throws Exception {//model en estos metodos es para meterle lo que queramos que le llegue a la vista de vuelta en el return
        if(resultadoValidacion.hasErrors()){
            return "admin/editar_producto";
        }else{
            servicioReloj.guardarCambiosReloj(relojEditar);
            return gestionarReloj(model);
        }

    }
}