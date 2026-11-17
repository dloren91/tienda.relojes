package com.lorenzo.tienda.relojes.controllers;

import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioCategorias;
import com.lorenzo.tienda.relojes.servicios.ServicioUsuario;
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
public class ControladorUsuarios {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioCategorias servicioCategorias;

    @RequestMapping("gestionarUsuarios")
    public String gestionarUsuario(Model model){
        model.addAttribute("usuarios",servicioUsuario.obtenerUsuario());

        return "admin/gestionar/gestionar_usuarios";
    }
    @RequestMapping("registrarUsuario")
    public String registrarUsuario(Model model){
        Usuario nuevo = new Usuario();
        nuevo.setDireccion("1");
        model.addAttribute("nuevo",nuevo);
        //necesito obtener las categorias
        //para meterlas en este model de spring
        //y que lleguen al html del return
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());

        return "admin/registro/registro_usuario";
    }

    @RequestMapping("guardarUsuario")
    public String guardarUsuario(@ModelAttribute("nuevo") @Valid Usuario nuevo, BindingResult resultadoValidacion, Model model){
        System.out.println("recibido para registrar: " + nuevo);
        if(resultadoValidacion.hasErrors()){
            return "admin/registro/registro_usuario";
        }
        servicioUsuario.registrarUsuario(nuevo);
        return "admin/registro/registro_usuario_ok";
    }

    @RequestMapping("borrarUsuario")
    public String borrarUsuario(@RequestParam("id")Long id, Model model){
        System.out.println("id recibe para borrar usuarios de id: " + id);
        servicioUsuario.borrarUsuario(id);
        return gestionarUsuario(model);
    }
    @RequestMapping("editarUsuario")
    public String editarUsuario(@RequestParam("id")Long id, Model model){
        Usuario uEditar = servicioUsuario.obtenerUsuarioPorId(id);
        //uEditar.setIdCategoria(uEditar.getCategoria().getId());
        model.addAttribute("usuarioEditar",uEditar);
        model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
        return "admin/editar/editar_usuario";
    }

    //el metodo va a tender la ruta guardarCambiosVideojuego a esa ruta se llega al hacer click en el Guardar Cambios del form edicion
    @RequestMapping("guardarCambiosUsuario")
    public String guardarCambiosUsuario (
            //es el th:object del form
            @ModelAttribute("nuevo")
            //para validar con las reglas puestas con el @ en Videojuego.java
            @Valid
            Usuario usuarioEditar,//objeto que contiene lo incluido en el form
            BindingResult resultadoValidacion,
            Model model) throws Exception {//model en estos metodos es para meterle lo que queramos que le llegue a la vista de vuelta en el return
        if(resultadoValidacion.hasErrors()){
            return "admin/editar/editar_usuario";
        }else{
            servicioUsuario.guardarCambiosUsuario(usuarioEditar);
            return gestionarUsuario(model);
        }

    }
}