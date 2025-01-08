package com.microservice.metodoDePago.repository;

import com.microservice.metodoDePago.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
}
