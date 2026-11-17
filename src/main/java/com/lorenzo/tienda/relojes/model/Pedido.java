package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Usuario.class, optional = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductoPedido> productosPedido = new ArrayList<>();

    private String estado;

    // --- Paso 1: Datos de envío ---
    private String nombreCompleto;
    private String direccion;
    private String provincia;
    private String codigoPostal;  // Nuevo campo 1
    private String telefono;      // Nuevo campo 2
    private String observaciones; // Nuevo campo 3

    // --- Paso 2: Datos de pago ---
    private String titularTarjeta;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private boolean aPlazos;



    // --- Paso 3: Respuesta pregunta ---
    private String resPregunta1;
    private String resPregunta2;

    public Pedido() {
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ProductoPedido> getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(List<ProductoPedido> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    // --- Nuevos campos ---

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

    // --- Datos tarjeta ---

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
    public boolean isAPlazos() {
        return aPlazos;
    }

    public void setAPlazos(boolean aPlazos) {
        this.aPlazos = aPlazos;
    }
    public boolean isaPlazos() {
        return aPlazos;
    }

    public void setaPlazos(boolean aPlazos) {
        this.aPlazos = aPlazos;
    }

    public String getResPregunta2() {
        return resPregunta2;
    }

    public void setResPregunta2(String resPregunta2) {
        this.resPregunta2 = resPregunta2;
    }

    public String getResPregunta1() {
        return resPregunta1;
    }

    public void setResPregunta1(String resPregunta1) {
        this.resPregunta1 = resPregunta1;
    }
}