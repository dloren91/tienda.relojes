package com.lorenzo.tienda.relojes.servicios;

import com.lorenzo.tienda.relojes.model.Reloj;
import org.jspecify.annotations.Nullable;


import java.util.List;
import java.util.Map;

public interface ServicioReloj {

    void registrarReloj(Reloj r);

    List<Reloj> obtenerReloj();

    List<Reloj> obtenerRelojPorModelo(String modelo);

    List<Reloj> obtenerRelojPorModeloYcomienzoYfin(String modelo, int comienzo, int resultadosPorPagina);

    Reloj obtenerRelojPorId(long id);

    void borrarReloj(long id);

    void guardarCambiosReloj(Reloj relojEditar)  throws Exception;

    List <Map<String, Object>> obtenerJSONrelojes(String modelo, int comienzo);

    Map <String,Object> obtenerJSONRelojPorId(Long id);

    int obtenerTotalRelojes(String modelo);
}
