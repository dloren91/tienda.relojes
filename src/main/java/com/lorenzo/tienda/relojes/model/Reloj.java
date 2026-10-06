package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tabla_relojes")
public class Reloj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3,max = 40,message = "TITULO DEBE TENER ENTRE 3 Y 40 CARACTERES")
    @NotEmpty(message = "DEBES INSERTAR UN TITULO")
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÜü 0-9]+", message = "MODELO SOLO PUEDE TERNER LETRAS NUMEROS Y ESPACIOS")
    private String modelo;

    @Min(value = 1, message = "PRECIO MINIMO 1 EURO")
    @Max(value = 99999,message = "PRECIO MAXIMO ES 99999 EUROS")
    private double precio;

    @NotNull(message = "DEBES RELLENAR EL CAMPO")
    private String marca;
    private String material;
    private int resistenciaAgua;
    private boolean automatico;

    @Transient
    private MultipartFile fotoSubida;

    @Lob
    private byte[] imagenPortada;

    public Reloj(String modelo, double precio, String marca, String material, int resistenciaAgua, boolean automatico) {
        this.modelo = modelo;
        this.precio = precio;
        this.marca = marca;
        this.material = material;
        this.resistenciaAgua = resistenciaAgua;
        this.automatico = automatico;
    }

    public Reloj() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getResistenciaAgua() {
        return resistenciaAgua;
    }

    public void setResistenciaAgua(int resistenciaAgua) {
        this.resistenciaAgua = resistenciaAgua;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public MultipartFile getFotoSubida() {
        return fotoSubida;
    }

    public void setFotoSubida(MultipartFile fotoSubida) {
        this.fotoSubida = fotoSubida;
    }

    public byte[] getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(byte[] imagenPortada) {
        this.imagenPortada = imagenPortada;
    }
}