package com.microservice.pagos.microservice_pagos.feign;

import com.microservice.pagos.microservice_pagos.controller.dto.MetodoPagoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-metodoDePago", url = "http://localhost:8080")
public interface MetodoPagoClient {
    @GetMapping("/api/metodos-pago/{id}")
    MetodoPagoDto obtenerMetodoPagoPorId(@PathVariable("id") Long id);
}
