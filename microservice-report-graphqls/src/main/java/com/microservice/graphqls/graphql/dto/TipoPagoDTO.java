package com.microservice.graphqls.graphql.dto;

import com.microservice.graphqls.model.TipoPago;

public class TipoPagoDTO {
    private Long id;
    private String descripcion;

    public TipoPagoDTO(TipoPago tipoPago) {
        this.id = tipoPago.getIdTipoPago();
        this.descripcion = tipoPago.getDescripcion();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
