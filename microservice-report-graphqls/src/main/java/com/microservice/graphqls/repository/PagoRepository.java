package com.microservice.graphqls.repository;

import com.microservice.graphqls.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
