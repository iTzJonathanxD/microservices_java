package com.microservice.pagos.microservice_pagos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "id_metodo_pago", nullable = false)
    private Long idMetodoPago;

    @Column(name = "id_tipo_pago", nullable = false)
    private Long idTipoPago;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago = "Pendiente";

    public Pago() {
    }

    public Pago(Long idMetodoPago, Long idTipoPago, Double monto, LocalDateTime fechaPago, String estadoPago) {
        this.idMetodoPago = idMetodoPago;
        this.idTipoPago = idTipoPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
    }

    // Getters y setters
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public Long getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Long idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
