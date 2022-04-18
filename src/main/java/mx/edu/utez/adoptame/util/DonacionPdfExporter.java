package mx.edu.utez.adoptame.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.edu.utez.adoptame.model.Donacion;
import mx.edu.utez.adoptame.model.Usuario;

public class DonacionPdfExporter {

    private Logger logger = LoggerFactory.getLogger(DonacionPdfExporter.class);

    private List<Donacion> listDonaciones;

    public DonacionPdfExporter(List<Donacion> listDonaciones) {

        this.listDonaciones = listDonaciones;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(37, 62, 92));
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.white);

        cell.setPhrase(new Phrase("Monto", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha de donación", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Numero de autorización", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Estado", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Donacion donacion : listDonaciones) {
            table.addCell("$ " + String.valueOf(donacion.getMonto()));
            table.addCell(String.valueOf(donacion.getFechaDonacion()));
            table.addCell(String.valueOf(donacion.getAutorizacion()));
            table.addCell(Boolean.TRUE.equals(donacion.getEstado()) ? "Aprobado" : "Pendiente");
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER.rotate());
        try {
            Usuario donador = listDonaciones.get(0).getDonador();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setColor(new Color(37, 62, 92));

            // Tabla para la imagen de Adoptame
            PdfPTable tableImage = new PdfPTable(2);

            tableImage.setWidthPercentage(100);
            tableImage.setSpacingAfter(15);

            // No apliques bordes en esta tabla
            tableImage.getDefaultCell().setBorder(0);
            tableImage.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            tableImage.setWidths(new float[] { 0.25f, 4f });

            font.setSize(20);
            Paragraph nameCompany = new Paragraph("AdoptaME", font);
            Image logo = Image.getInstance("src/main/resources/static/images/logo.png");
            logo.scaleAbsoluteWidth(0.1f);
            
            tableImage.addCell(logo);
            tableImage.addCell(nameCompany);

            document.add(tableImage);
            
            // Titulo para el PDF
            font.setSize(18);
            Paragraph title = new Paragraph("Lista de todas las donaciones", font);
            document.add(title);

            // Usuario que solicita el PDF
            font.setSize(14);
            Paragraph nameAndLastName = new Paragraph("Usuario: " + donador.getNombre() + " " +  donador.getApellidos(), font);
            document.add(nameAndLastName);

            // Correo
            font.setSize(14);
            Paragraph correo = new Paragraph("Email: " + donador.getCorreo(), font);
            document.add(correo);

            // Información de las donaciones 
            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);
            table.setSpacingBefore(20);

            table.setWidths(new float[] { 3.5f, 3.0f, 3.0f, 1.5f });

            writeTableHeader(table);
            writeTableData(table);

            document.add(table);

        } catch (DocumentException | IOException de) {
            logger.error(de.getMessage());
            logger.error("Error al descargar el pdf");
        }
        document.close();
    }
}
