package com.microservice.graphqls.repository;

import com.microservice.graphqls.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
}
