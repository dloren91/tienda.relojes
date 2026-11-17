package com.lorenzo.tienda.relojes.constantesSQL;

public class ConstantesSQL {

    public final static String SQL_OBTENER_RELOJES_JSON =
            "SELECT tv.id,tv.modelo_reloj,tv.marca,tv.precio,tv.material,tv.resistencia_agua,tv.automatico,c.nombre as categoria FROM TABLA_RELOJES as tv, categoria as c where tv.categoria_id =c.id and lower(tv.modelo_reloj) like lower(:modelo) limit :comienzo , 10 ";

    public static final String SQL_OBTENER_RELOJ_JSON =
            "SELECT tv.id,tv.modelo_reloj,tv.marca,tv.precio,tv.material,tv.resistencia_agua,tv.automatico,c.nombre as categoria FROM TABLA_RELOJES as tv, categoria as c where tv.categoria_id =c.id and tv.id= :id";

    public static final String SQL_OBTENER_PRODUCTOS_CARRITO_JSON = """
        SELECT
            tv.id AS reloj_id,
            tv.precio AS precio,
            tv.modelo_reloj,
            pc.cantidad
        FROM tabla_relojes AS tv
        INNER JOIN producto_carrito AS pc ON tv.id = pc.reloj_id
        WHERE pc.carrito_id = :carrito_id
        """;
    public static final String SQL_BORRAR_PRODUCTOS_CARRITO =
            "delete from producto_carrito where carrito_id = :carrito_id";
    public static final String SQL_OBTENER_TOTAL_RELOJES =
            "select count(id) from TABLA_RELOJES where lower(MODELO_RELOJ) like :modelo";
}
