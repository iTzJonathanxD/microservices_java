package com.uleam.microservice_tipoPago.controller;

import com.uleam.microservice_tipoPago.model.TipoPago;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }

    /**
     * Maneja las actualizaciones de tipos de pago.
     * @param tipoPago Objeto TipoPago enviado por el cliente.
     * @return El mismo objeto para notificar a todos los suscriptores.
     */
    @MessageMapping("/actualizarTiposPago")
    @SendTo("/topic/tiposPago")
    public TipoPago notificarActualizacionTiposPago(TipoPago tipoPago) {
        return tipoPago;
    }
}
