package com.lorenzo.tienda.relojes.controllers;

import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.servicios.ServicioCategorias;
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

    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarRelojes")
    public String gestionarReloj(@RequestParam(value = "modelo", defaultValue = "") String modelo, @RequestParam(value = "comienzo", defaultValue = "0") Integer comienzo, Model model){
        model.addAttribute("relojes",servicioReloj.obtenerRelojPorModeloYcomienzoYfin(modelo, comienzo, 10));
        model.addAttribute("siguiente", comienzo +10);
        model.addAttribute("anterior", comienzo -10);
        model.addAttribute("total", servicioReloj.obtenerTotalRelojes(modelo));
        model.addAttribute("modelo", modelo);
        return "admin/gestionar/gestionar_productos";
    }
    @RequestMapping("registrarReloj")
    public String registrarReloj(Model model){
        Reloj nuevo = new Reloj();
        nuevo.setPrecio(1);
        model.addAttribute("nuevo",nuevo);
        //necesito obtener las categorias
        //para meterlas en este model de spring
        //y que lleguen al html del return
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());

        return "admin/registro/registro_producto";// es un html
    }

    @RequestMapping("guardarReloj")
    public String guardarReloj(@ModelAttribute("nuevo") @Valid Reloj nuevo, BindingResult resultadoValidacion, Model model){
        System.out.println("recibido para registrar: " + nuevo);
        if(resultadoValidacion.hasErrors()){
            model.addAttribute("categorias", servicioCategorias.obtenerCategorias());

            //si el videojuego tiene errores mandamos al usuario a el formulario de nuevo
            return "admin/registro/registro_producto";
        }
        servicioReloj.registrarReloj(nuevo);
        return "admin/registro/registro_producto_ok";
    }

    @RequestMapping("borrarReloj")
    public String borrarReloj(@RequestParam("id")Long id, Model model){
        System.out.println("id recibe para borrar relojes de id: " + id);
        servicioReloj.borrarReloj(id);
        return gestionarReloj("",0, model);
    }
    @RequestMapping("editarReloj")
    public String editarReloj(@RequestParam("id")Long id, Model model){
        Reloj rEditar = servicioReloj.obtenerRelojPorId(id);
        rEditar.setIdCategoria(rEditar.getCategoria().getId());

        model.addAttribute("relojEditar",rEditar);
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/editar/editar_producto";
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
            return "admin/editar/editar_producto";
        }else{
            servicioReloj.guardarCambiosReloj(relojEditar);
            return gestionarReloj("",0, model);
        }

    }
}