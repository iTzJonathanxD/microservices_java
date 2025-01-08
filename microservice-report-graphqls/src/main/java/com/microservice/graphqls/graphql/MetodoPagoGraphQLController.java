package com.microservice.graphqls.graphql;

import com.microservice.graphqls.graphql.dto.MetodoPagoDTO;
import com.microservice.graphqls.model.MetodoPago;
import com.microservice.graphqls.service.MetodoPagoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MetodoPagoGraphQLController {

    private final MetodoPagoService metodoPagoService;

    public MetodoPagoGraphQLController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    /**
     * Query para listar todos los métodos de pago.
     */
    @QueryMapping
    public List<MetodoPagoDTO> listarMetodosDePago() {
        return metodoPagoService.listarMetodosDePago()
                .stream()
                .map(MetodoPagoDTO::new) // Transforma a DTO
                .collect(Collectors.toList());
    }

    /**
     * Query para obtener un método de pago por ID.
     */
    @QueryMapping
    public MetodoPagoDTO obtenerMetodoPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        MetodoPago metodoPago = metodoPagoService.buscarMetodoPagoPorId(id);

        if (metodoPago.getIdMetodoPago() == null) {
            throw new RuntimeException("El campo 'id' no puede ser nulo para el método de pago solicitado.");
        }

        return new MetodoPagoDTO(metodoPago);
    }

    /**
     * Mutation para crear un nuevo método de pago.
     */
    @MutationMapping
    public MetodoPagoDTO crearMetodoPago(@Argument String nombreMetodo) {
        if (nombreMetodo == null || nombreMetodo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del método de pago no puede estar vacío.");
        }

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setNombreMetodo(nombreMetodo);
        MetodoPago nuevoMetodoPago = metodoPagoService.crearMetodoPago(metodoPago);

        if (nuevoMetodoPago.getIdMetodoPago() == null) {
            throw new RuntimeException("Error al crear el método de pago. El campo 'id' no se generó.");
        }

        return new MetodoPagoDTO(nuevoMetodoPago);
    }

    /**
     * Mutation para actualizar un método de pago existente.
     */
    @MutationMapping
    public MetodoPagoDTO actualizarMetodoPago(@Argument Long id, @Argument String nombreMetodo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        if (nombreMetodo == null || nombreMetodo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del método de pago no puede estar vacío.");
        }

        MetodoPago metodoPagoActualizado = new MetodoPago();
        metodoPagoActualizado.setNombreMetodo(nombreMetodo);

        MetodoPago metodoActualizado = metodoPagoService.actualizarMetodoPago(id, metodoPagoActualizado);

        if (metodoActualizado.getIdMetodoPago() == null) {
            throw new RuntimeException("Error al actualizar el método de pago. El campo 'id' no puede ser nulo.");
        }

        return new MetodoPagoDTO(metodoActualizado);
    }

    /**
     * Mutation para eliminar un método de pago.
     */
    @MutationMapping
    public String eliminarMetodoPago(@Argument Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        metodoPagoService.eliminarMetodoPago(id);
        return "Método de pago eliminado con éxito.";
    }
}
