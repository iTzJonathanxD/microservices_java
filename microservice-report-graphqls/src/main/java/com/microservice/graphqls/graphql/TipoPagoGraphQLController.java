package com.microservice.graphqls.graphql;

import com.microservice.graphqls.graphql.dto.TipoPagoDTO;
import com.microservice.graphqls.model.TipoPago;
import com.microservice.graphqls.service.TipoPagoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TipoPagoGraphQLController {

    private final TipoPagoService tipoPagoService;

    public TipoPagoGraphQLController(TipoPagoService tipoPagoService) {
        this.tipoPagoService = tipoPagoService;
    }

    /**
     * Query para listar todos los tipos de pago.
     */
    @QueryMapping
    public List<TipoPagoDTO> listarTiposDePago() {
        return tipoPagoService.listarTiposDePago()
                .stream()
                .map(TipoPagoDTO::new) // Transformar entidades a DTOs
                .collect(Collectors.toList());
    }

    /**
     * Query para obtener un tipo de pago por ID.
     */
    @QueryMapping
    public TipoPagoDTO obtenerTipoPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        TipoPago tipoPago = tipoPagoService.buscarTipoPagoPorId(id);

        if (tipoPago.getIdTipoPago() == null) {
            throw new RuntimeException("El campo 'id' no puede ser nulo para el tipo de pago solicitado.");
        }

        return new TipoPagoDTO(tipoPago);
    }

    /**
     * Mutation para crear un nuevo tipo de pago.
     */
    @MutationMapping
    public TipoPagoDTO crearTipoPago(@Argument String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del tipo de pago no puede estar vacía.");
        }

        TipoPago tipoPago = new TipoPago();
        tipoPago.setDescripcion(descripcion);
        TipoPago nuevoTipoPago = tipoPagoService.crearTipoPago(tipoPago);

        if (nuevoTipoPago.getIdTipoPago() == null) {
            throw new RuntimeException("Error al crear el tipo de pago. El campo 'id' no se generó.");
        }

        return new TipoPagoDTO(nuevoTipoPago);
    }

    /**
     * Mutation para actualizar un tipo de pago existente.
     */
    @MutationMapping
    public TipoPagoDTO actualizarTipoPago(@Argument Long id, @Argument String descripcion) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del tipo de pago no puede estar vacía.");
        }

        TipoPago tipoPagoActualizado = new TipoPago();
        tipoPagoActualizado.setDescripcion(descripcion);

        TipoPago tipoActualizado = tipoPagoService.actualizarTipoPago(id, tipoPagoActualizado);

        if (tipoActualizado.getIdTipoPago() == null) {
            throw new RuntimeException("Error al actualizar el tipo de pago. El campo 'id' no puede ser nulo.");
        }

        return new TipoPagoDTO(tipoActualizado);
    }

    /**
     * Mutation para eliminar un tipo de pago.
     */
    @MutationMapping
    public String eliminarTipoPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        tipoPagoService.eliminarTipoPago(id);
        return "Tipo de pago eliminado con éxito.";
    }
}
