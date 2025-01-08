package com.microservice.metodoDePago.service;

import com.microservice.metodoDePago.model.MetodoPago;
import com.microservice.metodoDePago.repository.MetodoPagoRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePdfService {

    private final MetodoPagoRepository metodoPagoRepository;

    public ReportePdfService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public byte[] generarReporteMetodosDePago() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            // Crear una nueva página PDF
            PDPage page = new PDPage();
            document.addPage(page);

            List<MetodoPago> metodosDePago = metodoPagoRepository.findAll();

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.setLeading(15);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Reporte de Métodos de Pago");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();

                contentStream.showText("ID          Nombre del Método");
                contentStream.newLine();
                contentStream.showText("--------------------------------");
                contentStream.newLine();

                for (MetodoPago metodo : metodosDePago) {
                    String linea = String.format("%-10s %-30s", metodo.getIdMetodoPago(), metodo.getNombreMetodo());
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
