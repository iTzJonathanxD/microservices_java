package com.microservice.metodoDePago.controller;

import com.microservice.metodoDePago.model.MetodoPago;
import com.microservice.metodoDePago.service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;
    private final SimpMessagingTemplate messagingTemplate;

    public MetodoPagoController(MetodoPagoService metodoPagoService, SimpMessagingTemplate messagingTemplate) {
        this.metodoPagoService = metodoPagoService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> crearMetodoPago(@Valid @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago metodoPagoCreado = metodoPagoService.crearMetodoPago(metodoPago);
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "create",
                            "data", metodoPagoCreado,
                            "mensaje", "Método de pago creado"
                    )
            );
            return ResponseEntity.ok(metodoPagoCreado);
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al crear método de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMetodoPagoPorId(@PathVariable Long id) {
        try {
            MetodoPago metodoPago = metodoPagoService.buscarMetodoPagoPorId(id);
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "view",
                            "data", metodoPago,
                            "mensaje", "Método de pago obtenido"
                    )
            );
            return ResponseEntity.ok(metodoPago);
        } catch (RuntimeException e) {
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al obtener método de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMetodoPago(
            @PathVariable Long id,
            @Valid @RequestBody MetodoPago metodoPagoActualizado) {
        try {
            MetodoPago metodoPagoActualizadoResultado = metodoPagoService.actualizarMetodoPago(id, metodoPagoActualizado);
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "update",
                            "data", metodoPagoActualizadoResultado,
                            "mensaje", "Método de pago actualizado"
                    )
            );
            return ResponseEntity.ok(metodoPagoActualizadoResultado);
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al actualizar método de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMetodoPago(@PathVariable Long id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "delete",
                            "data", Map.of("id", id),
                            "mensaje", "Método de pago eliminado"
                    )
            );
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/metodosPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al eliminar método de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MetodoPago>> listarMetodosDePago() {
        List<MetodoPago> lista = metodoPagoService.listarMetodosDePago();
        messagingTemplate.convertAndSend(
                "/topic/metodosPago",
                Map.of(
                        "action", "list",
                        "data", lista,
                        "mensaje", "Lista de métodos de pago obtenida"
                )
        );
        return ResponseEntity.ok(lista);
    }
}
