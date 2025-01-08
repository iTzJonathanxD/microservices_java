package com.uleam.microservice_tipoPago;

import com.uleam.microservice_tipoPago.controller.TipoPagoController;
import com.uleam.microservice_tipoPago.model.TipoPago;
import com.uleam.microservice_tipoPago.service.TipoPagoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TipoPagoControllerTest {

    @Mock
    private TipoPagoService tipoPagoService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private TipoPagoController tipoPagoController;

    public TipoPagoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTipoPago() {
        // Arrange
        TipoPago tipoPago = new TipoPago();
        tipoPago.setDescripcion("Pago en efectivo");

        TipoPago tipoPagoCreado = new TipoPago();
        tipoPagoCreado.setIdTipoPago(1L);
        tipoPagoCreado.setDescripcion("Pago en efectivo");

        when(tipoPagoService.crearTipoPago(tipoPago)).thenReturn(tipoPagoCreado);

        // Act
        ResponseEntity<?> response = tipoPagoController.crearTipoPago(tipoPago);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipoPagoCreado, response.getBody());
        verify(messagingTemplate).convertAndSend(
                eq("/topic/tiposPago"),
                eq(Map.of("action", "create", "data", tipoPagoCreado, "mensaje", "Tipo de pago creado"))
        );
    }

    @Test
    void testObtenerTipoPagoPorId() {
        // Arrange
        Long id = 1L;
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipoPago(id);
        tipoPago.setDescripcion("Pago con tarjeta");

        when(tipoPagoService.buscarTipoPagoPorId(id)).thenReturn(tipoPago);

        // Act
        ResponseEntity<?> response = tipoPagoController.obtenerTipoPagoPorId(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tipoPago, response.getBody());
        verify(messagingTemplate).convertAndSend(
                eq("/topic/tiposPago"),
                eq(Map.of("action", "view", "data", tipoPago, "mensaje", "Tipo de pago obtenido"))
        );
    }

    @Test
    void testListarTiposDePago() {
        // Arrange
        List<TipoPago> tiposDePago = List.of(
                new TipoPago(1L, "Pago en efectivo"),
                new TipoPago(2L, "Pago con tarjeta")
        );

        when(tipoPagoService.listarTiposDePago()).thenReturn(tiposDePago);

        // Act
        ResponseEntity<List<TipoPago>> response = tipoPagoController.listarTiposDePago();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tiposDePago, response.getBody());
        verify(messagingTemplate).convertAndSend(
                eq("/topic/tiposPago"),
                eq(Map.of("action", "list", "data", tiposDePago, "mensaje", "Lista de tipos de pago obtenida"))
        );
    }

    @Test
    void testActualizarTipoPago() {
        // Arrange
        Long id = 1L;
        TipoPago tipoPagoActualizado = new TipoPago();
        tipoPagoActualizado.setDescripcion("Pago actualizado");

        TipoPago resultado = new TipoPago();
        resultado.setIdTipoPago(id);
        resultado.setDescripcion("Pago actualizado");

        when(tipoPagoService.actualizarTipoPago(id, tipoPagoActualizado)).thenReturn(resultado);

        // Act
        ResponseEntity<?> response = tipoPagoController.actualizarTipoPago(id, tipoPagoActualizado);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resultado, response.getBody());
        verify(messagingTemplate).convertAndSend(
                eq("/topic/tiposPago"),
                eq(Map.of("action", "update", "data", resultado, "mensaje", "Tipo de pago actualizado"))
        );
    }

    @Test
    void testEliminarTipoPago() {
        // Arrange
        Long id = 1L;

        doNothing().when(tipoPagoService).eliminarTipoPago(id);

        // Act
        ResponseEntity<?> response = tipoPagoController.eliminarTipoPago(id);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(messagingTemplate).convertAndSend(
                eq("/topic/tiposPago"),
                eq(Map.of("action", "delete", "data", Map.of("id", id), "mensaje", "Tipo de pago eliminado"))
        );
    }
}
