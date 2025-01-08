package com.microservice.metodoDePago.controller;

import com.microservice.metodoDePago.service.ReportePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metodos-pago")
public class ReporteController {

    private final ReportePdfService reportePdfService;

    public ReporteController(ReportePdfService reportePdfService) {
        this.reportePdfService = reportePdfService;
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> descargarReporteMetodosDePago() {
        byte[] pdfBytes = reportePdfService.generarReporteMetodosDePago();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Reporte_Metodos_Pago.pdf");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
