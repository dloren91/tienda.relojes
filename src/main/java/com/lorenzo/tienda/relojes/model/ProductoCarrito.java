package com.lorenzo.tienda.relojes.model;


import jakarta.persistence.*;

@Entity
public class ProductoCarrito {

    @ManyToOne
    @JoinColumn(name = "reloj_id", referencedColumnName = "id")
    private Reloj reloj;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @Id
    @GeneratedValue
    private Long id;

    //campos extra
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Reloj getReloj() {
        return reloj;
    }

    public void setReloj(Reloj reloj) {
        this.reloj = reloj;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
