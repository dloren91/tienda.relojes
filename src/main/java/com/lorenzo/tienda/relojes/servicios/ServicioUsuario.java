package com.lorenzo.tienda.relojes.servicios;

import com.lorenzo.tienda.relojes.model.Usuario;


import java.util.List;
import java.util.Map;

public interface ServicioUsuario {



    void registrarUsuario(Usuario u);

    List<Usuario> obtenerUsuario();

    Usuario obtenerUsuarioPorEmailYpass(String email, String pass);

    Usuario obtenerUsuarioPorId(long id);

    void borrarUsuario(long id);

    void guardarCambiosUsuario(Usuario usuarioEditar)  throws Exception;

   // List <Map<String, Object>> obtenerJSONusuarios();

   // Map <String,Object> obtenerJSONUsuarioPorId(Long id);
}
