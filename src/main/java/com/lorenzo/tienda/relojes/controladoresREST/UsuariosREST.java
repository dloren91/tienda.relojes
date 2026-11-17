package com.lorenzo.tienda.relojes.controladoresREST;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//Es necesario para registrar usuarios en la parte publica

@RestController
@RequestMapping("RESTusuarios/")
public class UsuariosREST {

    @Autowired
    private ServicioUsuario servicioUsuario;


    @RequestMapping("registrarUsuario")
    public String registrarUsuario(@RequestParam Map<String,Object> formData){
        System.out.println("recibido form data: " + formData);
        //el from data tiene la info del usuario a registrar
        //pues vamos a registrarlo
        //transformar el formData a Usuario
        //vamos a hacerlo con una libreria llamada gson
        Gson gson = new Gson();
        JsonElement json = gson.toJsonTree(formData);
        Usuario u = gson.fromJson(json,Usuario.class);
        servicioUsuario.registrarUsuario(u);
        return "te has registrado correctamente, muy bien maquina mastodonte fiera ciclon <3";
    }
    //recordatorio todos estso metodos se llaman desde el js de index.html
    @RequestMapping("identificarUsuario")
    public String identificarUsuario(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            HttpServletRequest request){
        Usuario u = servicioUsuario.obtenerUsuarioPorEmailYpass(email,pass);
        String respuesta = "";
        if(u != null ){
        request.getSession().setAttribute("usuario_identificado", u);
        respuesta = "ok," + u.getNombre();
        }else{
            respuesta = "email o pass incorrectos";
        }
        return respuesta;

    }

    @RequestMapping("cerrarSesion")
    public String cerrarSesion(HttpServletRequest request){
        request.getSession().invalidate();
        return "ok";
    }

}