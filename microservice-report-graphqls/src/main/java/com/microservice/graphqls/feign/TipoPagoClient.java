package com.microservice.graphqls.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-tipoPago", url = "http://localhost:8080/api/tipos-pago")
public interface TipoPagoClient {
    @GetMapping("/{id}")
    void obtenerTipoPago(@PathVariable("id") Long id);
}
