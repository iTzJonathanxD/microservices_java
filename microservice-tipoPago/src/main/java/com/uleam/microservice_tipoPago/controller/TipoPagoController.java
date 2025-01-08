package com.uleam.microservice_tipoPago.controller;

import com.uleam.microservice_tipoPago.model.TipoPago;
import com.uleam.microservice_tipoPago.service.TipoPagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tipos-pago")
public class TipoPagoController {

    private final TipoPagoService tipoPagoService;
    private final SimpMessagingTemplate messagingTemplate;

    public TipoPagoController(TipoPagoService tipoPagoService, SimpMessagingTemplate messagingTemplate) {
        this.tipoPagoService = tipoPagoService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> crearTipoPago(@Valid @RequestBody TipoPago tipoPago) {
        try {
            TipoPago tipoPagoCreado = tipoPagoService.crearTipoPago(tipoPago);
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "create",
                            "data", tipoPagoCreado,
                            "mensaje", "Tipo de pago creado"
                    )
            );
            return ResponseEntity.ok(tipoPagoCreado);
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al crear tipo de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTipoPagoPorId(@PathVariable Long id) {
        try {
            TipoPago tipoPago = tipoPagoService.buscarTipoPagoPorId(id);
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "view",
                            "data", tipoPago,
                            "mensaje", "Tipo de pago obtenido"
                    )
            );
            return ResponseEntity.ok(tipoPago);
        } catch (RuntimeException e) {
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al obtener tipo de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTipoPago(
            @PathVariable Long id,
            @Valid @RequestBody TipoPago tipoPagoActualizado) {
        try {
            TipoPago tipoPagoActualizadoResultado = tipoPagoService.actualizarTipoPago(id, tipoPagoActualizado);
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "update",
                            "data", tipoPagoActualizadoResultado,
                            "mensaje", "Tipo de pago actualizado"
                    )
            );
            return ResponseEntity.ok(tipoPagoActualizadoResultado);
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al actualizar tipo de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTipoPago(@PathVariable Long id) {
        try {
            tipoPagoService.eliminarTipoPago(id);
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "delete",
                            "data", Map.of("id", id),
                            "mensaje", "Tipo de pago eliminado"
                    )
            );
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/tiposPago",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al eliminar tipo de pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TipoPago>> listarTiposDePago() {
        List<TipoPago> lista = tipoPagoService.listarTiposDePago();
        messagingTemplate.convertAndSend(
                "/topic/tiposPago",
                Map.of(
                        "action", "list",
                        "data", lista,
                        "mensaje", "Lista de tipos de pago obtenida"
                )
        );
        return ResponseEntity.ok(lista);
    }
}
