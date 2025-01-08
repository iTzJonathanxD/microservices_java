package com.microservice.pagos.microservice_pagos.service;

import com.microservice.pagos.microservice_pagos.model.Pago;
import com.microservice.pagos.microservice_pagos.repository.PagoRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePdfService {

    private final PagoRepository pagoRepository;

    public ReportePdfService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public byte[] generarReportePagos() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            List<Pago> pagos = pagoRepository.findAll();

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.setLeading(15);
                contentStream.newLineAtOffset(50, 750);

                // Título
                contentStream.showText("Reporte de Pagos");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();

                contentStream.showText(String.format("%-10s %-15s %-10s %-10s", "ID", "Monto", "Estado", "Fecha"));
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------------");
                contentStream.newLine();

                for (Pago pago : pagos) {
                    String linea = String.format(
                            "%-10s %-15s %-10s %-10s",
                            pago.getIdPago(),
                            pago.getMonto(),
                            pago.getEstadoPago(),
                            pago.getFechaPago().toString()
                    );
                    contentStream.showText(linea);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte PDF: " + e.getMessage(), e);
        }
    }
}
