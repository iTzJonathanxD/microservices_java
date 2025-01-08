package com.microservice.pagos.microservice_pagos.service;

import com.microservice.pagos.microservice_pagos.controller.dto.MetodoPagoDto;
import com.microservice.pagos.microservice_pagos.model.Pago;
import com.microservice.pagos.microservice_pagos.repository.PagoRepository;
import com.microservice.pagos.microservice_pagos.feign.MetodoPagoClient;
import com.microservice.pagos.microservice_pagos.feign.TipoPagoClient;
import com.microservice.pagos.microservice_pagos.response.MetodoPagoResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final MetodoPagoClient metodoPagoClient;
    private final TipoPagoClient tipoPagoClient;

    public PagoService(PagoRepository pagoRepository, MetodoPagoClient metodoPagoClient, TipoPagoClient tipoPagoClient) {
        this.pagoRepository = pagoRepository;
        this.metodoPagoClient = metodoPagoClient;
        this.tipoPagoClient = tipoPagoClient;
    }

    public Pago registrarPago(Pago pago) {
        validarPago(pago);

        validarExistenciaMetodoPago(pago.getIdMetodoPago());
        validarExistenciaTipoPago(pago.getIdTipoPago());

        return pagoRepository.save(pago);
    }

    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPagoPorId(Long id) {
        validarId(id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        validarId(id);
        validarPago(pagoActualizado);

        Pago pago = obtenerPagoPorId(id);

        validarExistenciaMetodoPago(pagoActualizado.getIdMetodoPago());
        validarExistenciaTipoPago(pagoActualizado.getIdTipoPago());

        if (pagoActualizado.getMonto() > 0) {
            pago.setMonto(pagoActualizado.getMonto());
        }

        pago.setFechaPago(pagoActualizado.getFechaPago());

        if (StringUtils.hasText(pagoActualizado.getEstadoPago()) &&
                List.of("Pendiente", "Completado", "Cancelado").contains(pagoActualizado.getEstadoPago())) {
            pago.setEstadoPago(pagoActualizado.getEstadoPago());
        }

        return pagoRepository.save(pago);
    }

    public void eliminarPago(Long id) {
        validarId(id);
        Pago pago = obtenerPagoPorId(id);
        pagoRepository.delete(pago);
    }

    private void validarPago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser nulo.");
        }
        if (pago.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
        }
        if (!StringUtils.hasText(pago.getEstadoPago())) {
            throw new IllegalArgumentException("El estado del pago no puede estar vacío.");
        }
        if (!List.of("Pendiente", "Completado", "Cancelado").contains(pago.getEstadoPago())) {
            throw new IllegalArgumentException("El estado del pago no es válido. Valores permitidos: Pendiente, Completado, Cancelado.");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
    }

    private void validarExistenciaMetodoPago(Long idMetodoPago) {
        try {
            metodoPagoClient.obtenerMetodoPagoPorId(idMetodoPago);
        } catch (Exception e) {
            throw new IllegalArgumentException("El método de pago con ID " + idMetodoPago + " no existe.");
        }
    }

    private void validarExistenciaTipoPago(Long idTipoPago) {
        try {
            tipoPagoClient.obtenerTipoPago(idTipoPago);
        } catch (Exception e) {
            throw new IllegalArgumentException("El tipo de pago con ID " + idTipoPago + " no existe.");
        }
    }
}
