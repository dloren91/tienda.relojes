package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria  {

    private String nombre;
    private String descripcion;

    // voy a especificar la asosciacion de Categoria
    // con videojuego
    // asociaciones en hibernate/jpa:
    // OneToOne: una categoria solo puede ser asignada a un producto
    // OneToMany: 1 categoria puede estar asociada a muchos productos
    // ManyToOne muchas categorias pueden estar asociadas a 1 producto
    // ManyToMany: muchas categorias pueden estra asignadas a muchos productos

    @OneToMany(mappedBy = "categoria")
    private List<Reloj> relojes = new ArrayList<Reloj>();


    @Id
    @GeneratedValue
    private int id;

    public Categoria() {
    }

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Reloj> getRelojes() {
        return relojes;
    }

    public void setRelojes(List<Reloj> relojes) {
        this.relojes = relojes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
