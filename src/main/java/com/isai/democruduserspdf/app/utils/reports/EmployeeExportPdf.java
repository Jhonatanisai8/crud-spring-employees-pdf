package com.isai.democruduserspdf.app.utils.reports;

import com.isai.democruduserspdf.app.models.Employee;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeExportPdf {
    private List<Employee> employeeList;

    private void writeHeadPDF(PdfPTable pdfTable) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GREEN);
        cell.setPadding(2);
        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setColor(Color.BLACK);
        cell.setPhrase(new Phrase("ID", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Nombres", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Apellidos", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Telefono", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Genero", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Salario", font));
        pdfTable.addCell(cell);
        cell.setPhrase(new Phrase("Fecha de Nacimiento", font));
        pdfTable.addCell(cell);
    }

    private void writeTableData(PdfPTable pdfTable) {
        for (Employee employee : employeeList) {
            pdfTable.addCell(String.valueOf(employee.getIdEmployee()));
            pdfTable.addCell(employee.getFirstName());
            pdfTable.addCell(employee.getLastName());
            pdfTable.addCell(employee.getEmail());
            pdfTable.addCell(employee.getPhone());
            pdfTable.addCell(employee.getSex());
            pdfTable.addCell(String.valueOf(employee.getSalary()));
            pdfTable.addCell(employee.getBirthDate().toString());
        }
    }

    public void exportPdf(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(12);
        font.setColor(Color.BLACK);
        Paragraph title = new Paragraph("Reporte de Empleados", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        PdfPTable pdfTable = new PdfPTable(8);
        pdfTable.setWidthPercentage(100);
        pdfTable.setSpacingBefore(15);
        pdfTable.setWidths(new float[]{1f, 2.5f, 2.5f, 5f, 3f, 1.8f, 1.8f, 2.6f});
        pdfTable.setWidthPercentage(110);
        writeHeadPDF(pdfTable);
        writeTableData(pdfTable);
        document.add(pdfTable);
        document.close();
    }

}
