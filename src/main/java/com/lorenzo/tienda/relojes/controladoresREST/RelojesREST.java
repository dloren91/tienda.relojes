package com.lorenzo.tienda.relojes.controladoresREST;

import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.responseData.InfoRelojes;
import com.lorenzo.tienda.relojes.servicios.ServicioReloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("RESTrelojes/")
public class RelojesREST {

    @Autowired
    private ServicioReloj servicioReloj;


//asociamos los datos que estan indexsados
@RequestMapping("listar")
public InfoRelojes listar(
        @RequestParam(name = "modelo", defaultValue = "") String modelo,
        @RequestParam(name = "comienzo",defaultValue = "0") Integer comienzo){

    int totalRelojes = servicioReloj.obtenerTotalRelojes(modelo);
    List<Map<String,Object>> relojes =  servicioReloj.obtenerJSONrelojes(modelo,comienzo);
    //ahora necesitamos devolverle al js estas dos cosas
    //para ello vamos a crear un nuevo tipo de dato
    InfoRelojes info = new InfoRelojes();
    info.setTotalRelojes(totalRelojes);
    info.setRelojes(relojes);
    return info;

    //return servicioReloj.obtenerJSONrelojes(modelo, comienzo);
}

    @RequestMapping("obtener-reloj")
    public Map<String, Object> obtenerReloj(@RequestParam("id") Long id){
        return servicioReloj.obtenerJSONRelojPorId(id);
    }
}