package com.microservice.pagos.microservice_pagos;

import com.microservice.pagos.microservice_pagos.controller.PagoController;
import com.microservice.pagos.microservice_pagos.model.Pago;
import com.microservice.pagos.microservice_pagos.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagoControllerTest {

    @Mock
    private PagoService pagoService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private PagoController pagoController;

    private Pago pago;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pago = new Pago();
        pago.setIdPago(1L);
        pago.setIdMetodoPago(2L);
        pago.setIdTipoPago(3L);
        pago.setMonto(100.0);
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago("Pendiente");
    }

    @Test
    void testRegistrarPago() {
        when(pagoService.registrarPago(any(Pago.class))).thenReturn(pago);

        ResponseEntity<?> response = pagoController.registrarPago(pago);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pago, response.getBody());
        verify(pagoService, times(1)).registrarPago(pago);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/pagos"), anyMap());
    }

    @Test
    void testActualizarPago() {
        Pago pagoActualizado = new Pago(2L, 3L, 150.0, LocalDateTime.now(), "Completado");
        pagoActualizado.setIdPago(1L);

        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(pagoActualizado);

        ResponseEntity<?> response = pagoController.actualizarPago(1L, pagoActualizado);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pagoActualizado, response.getBody());
        verify(pagoService, times(1)).actualizarPago(1L, pagoActualizado);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/pagos"), anyMap());
    }

    @Test
    void testEliminarPago() {
        doNothing().when(pagoService).eliminarPago(1L);

        ResponseEntity<?> response = pagoController.eliminarPago(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(pagoService, times(1)).eliminarPago(1L);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/pagos"), anyMap());
    }

    @Test
    void testObtenerPagos() {
        List<Pago> pagos = List.of(pago);
        when(pagoService.obtenerPagos()).thenReturn(pagos);

        ResponseEntity<List<Pago>> response = pagoController.obtenerPagos();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pagos, response.getBody());
        verify(pagoService, times(1)).obtenerPagos();
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/pagos"), anyMap());
    }

    @Test
    void testObtenerPagoPorId() {
        when(pagoService.obtenerPagoPorId(1L)).thenReturn(pago);

        ResponseEntity<?> response = pagoController.obtenerPagoPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pago, response.getBody());
        verify(pagoService, times(1)).obtenerPagoPorId(1L);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/pagos"), anyMap());
    }
}
