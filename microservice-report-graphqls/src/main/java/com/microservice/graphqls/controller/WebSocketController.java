package com.microservice.graphqls.controller;

import com.microservice.graphqls.model.Pago;
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

    @MessageMapping("/actualizarPagos")
    @SendTo("/topic/pagos")
    public Pago notificarActualizacionPagos(Pago pago) {
        return pago;
    }
}
