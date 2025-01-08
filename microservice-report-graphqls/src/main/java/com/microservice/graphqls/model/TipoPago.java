package com.microservice.graphqls.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tipo_pago")
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pago")
    private Long idTipoPago;

    @Column(name = "descripcion", nullable = false, unique = true, length = 50)
    private String descripcion;

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
