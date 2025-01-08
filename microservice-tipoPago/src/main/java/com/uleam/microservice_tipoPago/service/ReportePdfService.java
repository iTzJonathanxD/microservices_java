package com.uleam.microservice_tipoPago.service;

import com.uleam.microservice_tipoPago.model.TipoPago;
import com.uleam.microservice_tipoPago.repository.TipoPagoRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePdfService {

    private final TipoPagoRepository tipoPagoRepository;

    public ReportePdfService(TipoPagoRepository tipoPagoRepository) {
        this.tipoPagoRepository = tipoPagoRepository;
    }

    public byte[] generarReporteTiposDePago() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            List<TipoPago> tiposDePago = tipoPagoRepository.findAll();

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.setLeading(15);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Reporte de Tipos de Pago");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();

                contentStream.showText("ID          Descripción");
                contentStream.newLine();
                contentStream.showText("--------------------------------");
                contentStream.newLine();

                for (TipoPago tipo : tiposDePago) {
                    String linea = String.format("%-10s %-30s", tipo.getIdTipoPago(), tipo.getDescripcion());
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
