package com.lorenzo.tienda.relojes.servicios;

import com.lorenzo.tienda.relojes.model.Categoria;


import java.util.List;
import java.util.Map;

public interface ServicioCategorias {

    void registrarCategoria(Categoria c);

    List<Categoria> obtenerCategorias();

    Categoria obtenerCategoriaPorId(long id);

    void borrarCategoria(long id);

    void guardarCambiosCategoria(Categoria categoriaEditar)  throws Exception;

    // List <Map<String, Object>> obtenerJSONusuarios();

    // Map <String,Object> obtenerJSONUsuarioPorId(Long id);
}
