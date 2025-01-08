package com.microservice.graphqls.graphql.dto;

import com.microservice.graphqls.model.Pago;

import java.time.LocalDateTime;

public class PagoDTO {
    private Long id;
    private Long idMetodoPago;
    private Long idTipoPago;
    private Double monto;
    private LocalDateTime fechaPago;
    private String estadoPago;

    public PagoDTO(Pago pago) {
        this.id = pago.getIdPago();
        this.idMetodoPago = pago.getIdMetodoPago();
        this.idTipoPago = pago.getIdTipoPago();
        this.monto = pago.getMonto();
        this.fechaPago = pago.getFechaPago();
        this.estadoPago = pago.getEstadoPago();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getIdMetodoPago() {
        return idMetodoPago;
    }

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }
}
