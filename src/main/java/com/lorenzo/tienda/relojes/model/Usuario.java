package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tabla_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Introduce un correo electrónico válido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String pass;

    @OneToOne
    private Carrito carrito;

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;
    private String direccion;
    private String cuentaBancaria;

    // Constructor vacío (necesario para frameworks como Spring/Hibernate/Jackson)
    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(Long id, String email, String pass, String nombre, String direccion, String cuentaBancaria) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentaBancaria = cuentaBancaria;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cuentaBancaria='" + cuentaBancaria + '\'' +
                '}';
    }
}