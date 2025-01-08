package com.microservice.pagos.microservice_pagos.controller;

import com.microservice.pagos.microservice_pagos.model.Pago;
import com.microservice.pagos.microservice_pagos.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService pagoService;
    private final SimpMessagingTemplate messagingTemplate;

    public PagoController(PagoService pagoService, SimpMessagingTemplate messagingTemplate) {
        this.pagoService = pagoService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> registrarPago(@Valid @RequestBody Pago pago) {
        try {
            Pago nuevoPago = pagoService.registrarPago(pago);
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "create",
                            "data", nuevoPago,
                            "mensaje", "Pago registrado"
                    )
            );
            return ResponseEntity.ok(nuevoPago);
        } catch (IllegalArgumentException e) {
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al registrar pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error inesperado: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long id, @Valid @RequestBody Pago pago) {
        try {
            Pago pagoActualizado = pagoService.actualizarPago(id, pago);
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "update",
                            "data", pagoActualizado,
                            "mensaje", "Pago actualizado"
                    )
            );
            return ResponseEntity.ok(pagoActualizado);
        } catch (RuntimeException e) {
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al actualizar pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "delete",
                            "data", Map.of("id", id),
                            "mensaje", "Pago eliminado"
                    )
            );
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al eliminar pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Pago>> obtenerPagos() {
        List<Pago> lista = pagoService.obtenerPagos();
        messagingTemplate.convertAndSend(
                "/topic/pagos",
                Map.of(
                        "action", "list",
                        "data", lista,
                        "mensaje", "Lista de pagos obtenida"
                )
        );
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPagoPorId(@PathVariable Long id) {
        try {
            Pago pago = pagoService.obtenerPagoPorId(id);
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "view",
                            "data", pago,
                            "mensaje", "Detalle del pago obtenido"
                    )
            );
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            messagingTemplate.convertAndSend(
                    "/topic/pagos",
                    Map.of(
                            "action", "error",
                            "mensaje", "Error al obtener pago: " + e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
