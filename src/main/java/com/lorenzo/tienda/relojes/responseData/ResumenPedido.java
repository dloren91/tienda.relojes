package com.lorenzo.tienda.relojes.responseData;

import java.util.List;
import java.util.Map;

//esta es la informacion que recibira el usuario para confirmar el pedido
public class ResumenPedido {

    //datos paso 1;
    private String nombreCompleto;
    private String direccion;
    private String codigoPostal;
    private String provincia;
    private String telefono;
    private String observaciones;

    //datos paso 2:
    private String titularTarjeta;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private boolean aPlazos;

    private List<Map<String,Object>> relojes;

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public List<Map<String, Object>> getRelojes() {
        return relojes;
    }

    public void setRelojes(List<Map<String, Object>> relojes) {
        this.relojes = relojes;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isaPlazos() {
        return aPlazos;
    }

    public void setaPlazos(boolean aPlazos) {
        this.aPlazos = aPlazos;
    }
}
