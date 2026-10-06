package com.lorenzo.tienda.relojes.servicios;

import com.lorenzo.tienda.relojes.model.Reloj;


import java.util.List;

public interface ServicioReloj {

    void registrarReloj(Reloj r);

    List<Reloj> obtenerReloj();

    Reloj obtenerRelojPorId(long id);

    void borrarReloj(long id);

    void guardarCambiosReloj(Reloj relojEditar)  throws Exception;
}
