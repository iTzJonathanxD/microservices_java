package com.microservice.pagos.microservice_pagos.response;

public class MetodoPagoResponse {
    private Long idMetodoPago;
    private String nombreMetodo;

    // Constructor completo
    public MetodoPagoResponse(Long idMetodoPago, String nombreMetodo) {
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
}
