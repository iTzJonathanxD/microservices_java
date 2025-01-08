package com.uleam.microservice_tipoPago.controller;

import com.uleam.microservice_tipoPago.service.ReportePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos-pago")
public class ReporteController {

    private final ReportePdfService reportePdfService;

    public ReporteController(ReportePdfService reportePdfService) {
        this.reportePdfService = reportePdfService;
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> descargarReporteTiposDePago() {
        byte[] pdfBytes = reportePdfService.generarReporteTiposDePago();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Reporte_Tipos_Pago.pdf");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
