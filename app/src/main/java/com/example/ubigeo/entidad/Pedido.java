package com.example.ubigeo.entidad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private int idPedido;
    private String fechaRegistro;
    private String fechaEntrega;
    private String lugarEntrega;
//    private String estado;
//    private String idCliente;
//    private String idUbigeo;
//    private String idUsuario;


    @Override
    public String toString(){
        return lugarEntrega;
    }

}
