package com.microservice.graphqls.graphql.dto;

import com.microservice.graphqls.model.MetodoPago;

public class MetodoPagoDTO {
    private Long id;
    private String nombreMetodo;

    public MetodoPagoDTO(MetodoPago metodoPago) {
        this.id = metodoPago.getIdMetodoPago();
        this.nombreMetodo = metodoPago.getNombreMetodo();
    }

    public Long getId() {
        return id;
    }
    public String getNombreMetodo() {
        return nombreMetodo;
    }
}
