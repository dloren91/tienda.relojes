package com.lorenzo.tienda.relojes.controllers;

import com.lorenzo.tienda.relojes.model.Categoria;
import com.lorenzo.tienda.relojes.servicios.ServicioCategorias;
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
public class ControladorCategorias {

    @Autowired
    private ServicioCategorias servicioCategorias;


    @RequestMapping("gestionarCategorias")
    public String gestionarCategoria(Model model){
        model.addAttribute("categorias",servicioCategorias.obtenerCategorias());

        return "admin/gestionar/gestionar_categorias";
    }
    @RequestMapping("registrarCategoria")
    public String registrarCategoria(Model model){
        Categoria nuevo = new Categoria();
        model.addAttribute("nuevo",nuevo);
        //necesito obtener las categorias
        //para meterlas en este model de spring
        //y que lleguen al html del return
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());

        return "admin/registro/registro_categoria";// es un html
    }

    @RequestMapping("guardarCategoria")
    public String guardarCategoria(@ModelAttribute("nuevo") @Valid Categoria nuevo, BindingResult resultadoValidacion, Model model){
        System.out.println("recibido para registrar: " + nuevo);
        if(resultadoValidacion.hasErrors()){
            //si el videojuego tiene errores mandamos al usuario a el formulario de nuevo
            return "admin/registro_categoria";
        }
        servicioCategorias.registrarCategoria(nuevo);
        return "admin/registro/registro_categoria_ok";
    }

    @RequestMapping("borrarCategoria")
    public String borrarCategoria(@RequestParam("id")Long id, Model model){
        System.out.println("id recibe para borrar categorias de id: " + id);
        servicioCategorias.borrarCategoria(id);
        return gestionarCategoria(model);
    }
    @RequestMapping("editarCategoria")
    public String editarCategoria(@RequestParam("id")Long id, Model model){
        Categoria cEditar = servicioCategorias.obtenerCategoriaPorId(id);
        //cEditar.setIdCategoria(cEditar.getCategoria().getId());

        model.addAttribute("categoriaEditar",cEditar);
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/editar/editar_categoria";
    }

    //el metodo va a tender la ruta guardarCambiosVideojuego a esa ruta se llega al hacer click en el Guardar Cambios del form edicion
    @RequestMapping("guardarCambiosCategoria")
    public String guardarCambiosCategoria (
            //es el th:object del form
            @ModelAttribute("nuevo")
            //para validar con las reglas puestas con el @ en Videojuego.java
            @Valid
            Categoria categoriaEditar,//objeto que contiene lo incluido en el form
            BindingResult resultadoValidacion,
            Model model) throws Exception {//model en estos metodos es para meterle lo que queramos que le llegue a la vista de vuelta en el return
        if(resultadoValidacion.hasErrors()){
            return "admin/editar/editar_categoria";
        }else{
            servicioCategorias.guardarCambiosCategoria(categoriaEditar);
            return gestionarCategoria(model);
        }

    }
}
