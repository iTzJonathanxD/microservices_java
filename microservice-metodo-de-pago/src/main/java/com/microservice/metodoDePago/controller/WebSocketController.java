package com.microservice.metodoDePago.controller;

import com.microservice.metodoDePago.model.MetodoPago;
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
    @MessageMapping("/actualizarMetodosPago")
    @SendTo("/topic/metodosPago")
    public MetodoPago notificarActualizacionMetodosPago(MetodoPago metodoPago) {
        return metodoPago;
    }
}
