package com.microservice.pagos.microservice_pagos.repository;

import com.microservice.pagos.microservice_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
