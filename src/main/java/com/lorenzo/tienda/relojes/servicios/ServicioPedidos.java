package com.lorenzo.tienda.relojes.servicios;

import com.lorenzo.tienda.relojes.model.Pedido;
import com.lorenzo.tienda.relojes.responseData.ResumenPedido;

import java.util.List;

public interface ServicioPedidos {

    //gestion en administracion
    List<Pedido> obtenerPedidios();
    Pedido obtenerPedidosPorId(long id);
    void actualizarEstadoPedido(long id,String estado);

    //operciones para la parte publica - ajax/api rest
    void procesarPaso1(String nombreCompleto, String direccion, String provincia, String codigoPostal, String telefono, String observaciones, long idUsuario);
    void procesarPaso2(String titular, String numero, String tipoTarjeta, boolean aPlazos, long idUsuario);
    void procesarPaso3(String resPregunta1, String resPregunta2, long idUsuario);
    ResumenPedido obtenerResumenPedido(long idUsuario);
    void confirmarPedido(long idUsuario);


}
