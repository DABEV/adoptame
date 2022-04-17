package mx.edu.utez.adoptame.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import mx.edu.utez.adoptame.model.Donacion;

public class DonacionPdfExporter {

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
            table.addCell(String.valueOf(donacion.getMonto()));
            table.addCell(String.valueOf(donacion.getFechaDonacion()));
            table.addCell(String.valueOf(donacion.getAutorizacion()));
            table.addCell(String.valueOf(donacion.getEstado()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER.rotate());

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(new Color(37, 62, 92));
        font.setSize(18);

        Paragraph title = new Paragraph("Lista de todas las donaciones", font);
        document.add(title);

        PdfPTable table = new PdfPTable(4);

        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        table.setWidths(new float[] { 3.5f, 3.0f, 3.0f, 1.5f });

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();

    }
}
