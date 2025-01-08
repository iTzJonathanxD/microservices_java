package com.microservice.graphqls.service;

import com.microservice.graphqls.model.MetodoPago;
import com.microservice.graphqls.repository.MetodoPagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public MetodoPago crearMetodoPago(MetodoPago metodoPago) {
        validarMetodoPago(metodoPago);
        return metodoPagoRepository.save(metodoPago);
    }

    public List<MetodoPago> listarMetodosDePago() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago buscarMetodoPagoPorId(Long id) {
        validarId(id);
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado con el ID: " + id));
    }

    public MetodoPago actualizarMetodoPago(Long id, MetodoPago metodoPagoActualizado) {
        validarId(id);
        validarMetodoPago(metodoPagoActualizado);
        MetodoPago metodoPago = buscarMetodoPagoPorId(id);
        metodoPago.setNombreMetodo(metodoPagoActualizado.getNombreMetodo());
        return metodoPagoRepository.save(metodoPago);
    }

    public void eliminarMetodoPago(Long id) {
        validarId(id);
        MetodoPago metodoPago = buscarMetodoPagoPorId(id);
        metodoPagoRepository.delete(metodoPago);
    }

    private void validarMetodoPago(MetodoPago metodoPago) {
        if (metodoPago == null) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo.");
        }
        if (!StringUtils.hasText(metodoPago.getNombreMetodo())) {
            throw new IllegalArgumentException("El nombre del método de pago no puede estar vacío.");
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
    }
}
