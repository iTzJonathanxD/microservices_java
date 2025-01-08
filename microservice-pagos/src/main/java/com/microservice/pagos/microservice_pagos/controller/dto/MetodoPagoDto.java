package com.microservice.pagos.microservice_pagos.controller.dto;

import com.microservice.pagos.microservice_pagos.model.Pago;

public class MetodoPagoDto {
    private Long idMetodoPago;
    private String nombreMetodo;

    public MetodoPagoDto() {
    }

    public MetodoPagoDto(Long idMetodoPago, String nombreMetodo) {
        this.idMetodoPago = idMetodoPago;
        this.nombreMetodo = nombreMetodo;
    }

    // Getters y Setters
    public Long getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Long idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public Pago orElseThrow(Object o) {
        return null;
    }
}
