package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//tabla que va ayudarnos a ver si ya tenemos registros iniciales u otras cosas
@Entity
public class Configuracion {
    private boolean registrosIniciales;

    @Id
    @GeneratedValue
    private int id;

    public void setRegistrosIniciales(boolean registrosIniciales) {
        this.registrosIniciales = registrosIniciales;
    }

    public boolean isRegistrosIniciales(){
        return registrosIniciales;
    }

}
