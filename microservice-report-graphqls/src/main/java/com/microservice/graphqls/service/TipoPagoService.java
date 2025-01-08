package com.microservice.graphqls.service;

import com.microservice.graphqls.model.TipoPago;
import com.microservice.graphqls.repository.TipoPagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TipoPagoService {

    private final TipoPagoRepository tipoPagoRepository;

    public TipoPagoService(TipoPagoRepository tipoPagoRepository) {
        this.tipoPagoRepository = tipoPagoRepository;
    }

    public TipoPago crearTipoPago(TipoPago tipoPago) {
        validarTipoPago(tipoPago);
        return tipoPagoRepository.save(tipoPago);
    }

    public List<TipoPago> listarTiposDePago() {
        return tipoPagoRepository.findAll();
    }

    public TipoPago buscarTipoPagoPorId(Long id) {
        return tipoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado con ID: " + id));
    }

    public TipoPago actualizarTipoPago(Long id, TipoPago tipoPagoActualizado) {
        validarId(id);
        validarTipoPago(tipoPagoActualizado);
        TipoPago tipoPago = buscarTipoPagoPorId(id);
        tipoPago.setDescripcion(tipoPagoActualizado.getDescripcion());
        return tipoPagoRepository.save(tipoPago);
    }

    public void eliminarTipoPago(Long id) {
        validarId(id);
        TipoPago tipoPago = buscarTipoPagoPorId(id);
        tipoPagoRepository.delete(tipoPago);
    }

    private void validarTipoPago(TipoPago tipoPago) {
        if (tipoPago == null) {
            throw new IllegalArgumentException("El tipo de pago no puede ser nulo.");
        }
        if (!StringUtils.hasText(tipoPago.getDescripcion())) {
            throw new IllegalArgumentException("La descripción del tipo de pago no puede estar vacía.");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
    }
}
