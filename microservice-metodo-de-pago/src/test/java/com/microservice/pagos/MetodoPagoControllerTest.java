package com.microservice.pagos;

import com.microservice.metodoDePago.controller.MetodoPagoController;
import com.microservice.metodoDePago.model.MetodoPago;
import com.microservice.metodoDePago.service.MetodoPagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MetodoPagoControllerTest {

    @Mock
    private MetodoPagoService metodoPagoService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MetodoPagoController metodoPagoController;

    private MetodoPago metodoPago;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        metodoPago = new MetodoPago();
        metodoPago.setIdMetodoPago(1L);
        metodoPago.setNombreMetodo("Tarjeta de Crédito");
    }

    @Test
    void testCrearMetodoPago() {
        when(metodoPagoService.crearMetodoPago(any(MetodoPago.class))).thenReturn(metodoPago);

        ResponseEntity<?> response = metodoPagoController.crearMetodoPago(metodoPago);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(metodoPago, response.getBody());
        verify(metodoPagoService, times(1)).crearMetodoPago(metodoPago);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/metodosPago"), anyMap());
    }

    @Test
    void testObtenerMetodoPagoPorId() {
        when(metodoPagoService.buscarMetodoPagoPorId(1L)).thenReturn(metodoPago);

        ResponseEntity<?> response = metodoPagoController.obtenerMetodoPagoPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(metodoPago, response.getBody());
        verify(metodoPagoService, times(1)).buscarMetodoPagoPorId(1L);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/metodosPago"), anyMap());
    }

    @Test
    void testActualizarMetodoPago() {
        MetodoPago metodoPagoActualizado = new MetodoPago();
        metodoPagoActualizado.setIdMetodoPago(1L);
        metodoPagoActualizado.setNombreMetodo("Tarjeta de Débito");

        when(metodoPagoService.actualizarMetodoPago(eq(1L), any(MetodoPago.class))).thenReturn(metodoPagoActualizado);

        ResponseEntity<?> response = metodoPagoController.actualizarMetodoPago(1L, metodoPagoActualizado);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(metodoPagoActualizado, response.getBody());
        verify(metodoPagoService, times(1)).actualizarMetodoPago(1L, metodoPagoActualizado);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/metodosPago"), anyMap());
    }

    @Test
    void testEliminarMetodoPago() {
        doNothing().when(metodoPagoService).eliminarMetodoPago(1L);

        ResponseEntity<?> response = metodoPagoController.eliminarMetodoPago(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(metodoPagoService, times(1)).eliminarMetodoPago(1L);
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/metodosPago"), anyMap());
    }

    @Test
    void testListarMetodosDePago() {
        List<MetodoPago> metodos = List.of(metodoPago);
        when(metodoPagoService.listarMetodosDePago()).thenReturn(metodos);

        ResponseEntity<List<MetodoPago>> response = metodoPagoController.listarMetodosDePago();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(metodos, response.getBody());
        verify(metodoPagoService, times(1)).listarMetodosDePago();
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/metodosPago"), anyMap());
    }
}
