package mx.edu.utez.adoptame.util;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.model.Usuario;

public class DonacionPdfExporter {

    private Logger logger = LoggerFactory.getLogger(DonacionPdfExporter.class);

    private Donacion donacion;

    public DonacionPdfExporter(Donacion donacion) {
        this.donacion = donacion;
    }

    private void setHeader (Document document) throws BadElementException, IOException {
        // Títulos
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setColor(new Color(37, 62, 92));

        // Tabla para la imagen de Adoptame
        PdfPTable tableImage = new PdfPTable(2);

        tableImage.setWidthPercentage(100);
        tableImage.setSpacingAfter(15);

        // No apliques bordes en esta tabla
        tableImage.getDefaultCell().setBorder(0);
        tableImage.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        tableImage.setWidths(new float[] { 0.25f, 4f });

        titleFont.setSize(24);
        Paragraph nameCompany = new Paragraph("AdoptaME", titleFont);
        Image logo = Image.getInstance("src/main/resources/static/images/logo.png");
        logo.scaleAbsoluteWidth(0.1f);
        
        tableImage.addCell(logo);
        tableImage.addCell(nameCompany);

        document.add(tableImage);
        
        // Titulo para el PDF
        titleFont.setSize(20);
        Paragraph title = new Paragraph("Recibo de donación", titleFont);
        title.setSpacingAfter(15);
        document.add(title);
    }

    private void setData (Paragraph infoField, Paragraph infoData, Document document) {
        infoField.setAlignment(Element.ALIGN_CENTER);
        infoData.setAlignment(Element.ALIGN_CENTER);

        infoData.setSpacingAfter(15);

        document.add(infoField);
        document.add(infoData);
    }

    private void setBody (Document document) {
        Usuario donador = donacion.getDonador();

        // Subtítulos
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        subtitleFont.setColor(new Color(37, 62, 92));
        subtitleFont.setSize(18);
        
        // info
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        infoFont.setSize(14);
        
        // Información del donador
        setData(new Paragraph("Nombre Completo:", subtitleFont), new Paragraph(donador.getNombre() + " " + donador.getApellidos(), infoFont), document);
        setData(new Paragraph("Correo Electrónico:", subtitleFont), new Paragraph(donador.getCorreo(), infoFont), document);
        setData(new Paragraph("Teléfono:", subtitleFont), new Paragraph(donador.getTelefono(), infoFont), document);
        setData(new Paragraph("Monto:", subtitleFont), new Paragraph("$ " + (donacion.getMonto() != null ? donacion.getMonto() : "0.0"), infoFont), document);
        setData(new Paragraph("Fecha:", subtitleFont), new Paragraph(String.valueOf(donacion.getFechaDonacion()), infoFont), document);
        setData(new Paragraph("¡Muchas gracias por su donación!", subtitleFont), new Paragraph("", infoFont), document);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            setHeader(document);
            setBody(document);

        } catch (DocumentException | IOException de) {
            logger.error(de.getMessage());
        }
        document.close();
    }
}
