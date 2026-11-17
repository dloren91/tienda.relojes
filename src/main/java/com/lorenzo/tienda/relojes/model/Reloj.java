package com.lorenzo.tienda.relojes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tabla_relojes")
public class Reloj {

    // --- Atributos / Campos ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotEmpty(message = "DEBES INSERTAR UN MODELO")
    @Size(min = 3, max = 40, message = "MODELO DEBE TENER ENTRE 3 Y 40 CARACTERES")
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÜü\\-:'& 0-9]+", message = "MODELO SOLO PUEDE TERNER LETRAS NUMEROS Y ESPACIOS")
    @Column(name = "modelo_reloj",length = 300)
    private String modelo;

    private String sku; //stock keeping unit // numero de control del producto

    @Min(value = 1, message = "PRECIO MINIMO 1 EURO")
    @Max(value = 999999999, message = "PRECIO MAXIMO ES 999999999 EUROS")
    private double precio;

    @NotBlank(message = "DEBES INSERTAR UN MARCA")
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÜü\\-:'& 0-9]+", message = "MARCA SOLO PUEDE TERNER LETRAS NUMEROS Y ESPACIOS")
    private String marca;

    @NotBlank(message = "DEBES INSERTAR UN MATERIAL")
    @Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÜü\\-:'& 0-9]+", message = "MATERIAL SOLO PUEDE TERNER LETRAS NUMEROS Y ESPACIOS")
    private String material;

    @Min(value = 1, message = "RESISTENCIA MINIMA 0 METROS")
    @Max(value = 999999999, message = "RESISTENCIA MAXIMA ES 999999999 METROS")
    private int resistenciaAgua;
    private boolean automatico;

    @Transient
    private MultipartFile fotoSubida;

    @Lob
    private byte[] imagenPortada;

//    @Lob
//    private byte[] imagenDetalles;

    @NotNull(message = "DEBES SELECCIONAR UNA CATEGORÍA")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Categoria categoria;



    //para gestionar la id del from y para otros usos
    @Transient
    private int idCategoria;

    // --- Constructores ---

    public Reloj() {
    }

    public Reloj(String modelo, double precio, String marca, String material, int resistenciaAgua, boolean automatico) {
        this.modelo = modelo;
        this.precio = precio;
        this.marca = marca;
        this.material = material;
        this.resistenciaAgua = resistenciaAgua;
        this.automatico = automatico;
    }

    // --- Getters y Setters ---

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}