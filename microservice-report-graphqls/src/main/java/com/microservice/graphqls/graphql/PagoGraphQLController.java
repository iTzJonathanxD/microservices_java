package com.microservice.graphqls.graphql;

import com.microservice.graphqls.graphql.dto.PagoDTO;
import com.microservice.graphqls.model.Pago;
import com.microservice.graphqls.service.PagoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PagoGraphQLController {

    private final PagoService pagoService;

    public PagoGraphQLController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * Query para listar todos los pagos.
     */
    @QueryMapping
    public List<PagoDTO> listarPagos() {
        return pagoService.obtenerPagos()
                .stream()
                .map(PagoDTO::new) // Transforma la entidad Pago a PagoDTO
                .collect(Collectors.toList());
    }

    /**
     * Query para obtener un pago por ID.
     */
    @QueryMapping
    public PagoDTO obtenerPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Pago pago = pagoService.obtenerPagoPorId(id);

        if (pago.getIdPago() == null) {
            throw new RuntimeException("El campo 'id' no puede ser nulo para el pago solicitado.");
        }

        return new PagoDTO(pago);
    }

    /**
     * Mutation para registrar un nuevo pago.
     */
    @MutationMapping
    public PagoDTO registrarPago(
            @Argument Long idMetodoPago,
            @Argument Long idTipoPago,
            @Argument Double monto,
            @Argument String estadoPago,
            @Argument String fechaPago
    ) {

        if (idMetodoPago == null || idMetodoPago <= 0) {
            throw new IllegalArgumentException("El ID del método de pago proporcionado no es válido.");
        }

        if (idTipoPago == null || idTipoPago <= 0) {
            throw new IllegalArgumentException("El ID del tipo de pago proporcionado no es válido.");
        }

        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
        }

        if (estadoPago == null || estadoPago.trim().isEmpty() ||
                !List.of("Pendiente", "Completado", "Cancelado").contains(estadoPago)) {
            throw new IllegalArgumentException("El estado del pago no es válido. Valores permitidos: Pendiente, Completado, Cancelado.");
        }

        if (fechaPago == null || fechaPago.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de pago no puede estar vacía.");
        }

        LocalDateTime fechaPagoConvertida;
        try {
            fechaPagoConvertida = LocalDateTime.parse(fechaPago);
        } catch (Exception e) {
            throw new IllegalArgumentException("El formato de la fecha de pago no es válido. Debe ser ISO 8601 (ejemplo: 2023-12-08T14:30:00).");
        }

        Pago pago = new Pago(idMetodoPago, idTipoPago, monto, fechaPagoConvertida, estadoPago);

        Pago nuevoPago = pagoService.registrarPago(pago);

        if (nuevoPago.getIdPago() == null) {
            throw new RuntimeException("Error al registrar el pago. El campo 'id' no se generó.");
        }

        return new PagoDTO(nuevoPago);
    }

    /**
     * Mutation para actualizar un pago existente.
     */
    @MutationMapping
    public PagoDTO actualizarPago(
            @Argument Long id,
            @Argument Double monto,
            @Argument String estadoPago) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Pago pagoActualizado = new Pago();

        if (monto != null && monto > 0) {
            pagoActualizado.setMonto(monto);
        }

        if (estadoPago != null && !estadoPago.trim().isEmpty() &&
                List.of("Pendiente", "Completado", "Cancelado").contains(estadoPago)) {
            pagoActualizado.setEstadoPago(estadoPago);
        }

        Pago pago = pagoService.actualizarPago(id, pagoActualizado);

        if (pago.getIdPago() == null) {
            throw new RuntimeException("Error al actualizar el pago. El campo 'id' no puede ser nulo.");
        }

        return new PagoDTO(pago);
    }

    /**
     * Mutation para eliminar un pago.
     */
    @MutationMapping
    public String eliminarPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        pagoService.eliminarPago(id);
        return "Pago eliminado con éxito.";
    }
}
