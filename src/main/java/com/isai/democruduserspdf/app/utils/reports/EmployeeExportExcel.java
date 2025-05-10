package com.isai.democruduserspdf.app.utils.reports;

import com.isai.democruduserspdf.app.models.Employee;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class EmployeeExportExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet leaves;
    private List<Employee> employeeList;

    public EmployeeExportExcel(
            List<Employee> employeeList) {
        this.employeeList = employeeList;
        this.workbook = new XSSFWorkbook();
        this.leaves = this.workbook.createSheet("Employees");
    }

    private void writeHeadExcel() {
        Row row = this.leaves.createRow(0);
        CellStyle style = this.workbook.createCellStyle();
        XSSFFont font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        Cell cell;
        final String COLUMNS[] = {"ID", "NOMBRES", "APELLIDOS", "EMAIL", "TELEFONO", "GENERO", "SALARIO", "FECHA NACIMIENTO"};
        for (int i = 0; i < COLUMNS.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(COLUMNS[i]);
            cell.setCellStyle(style);
        }
    }

    public void writeDataExcel() {
        int numberRows = 1;
        CellStyle style = this.workbook.createCellStyle();
        XSSFFont font = this.workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Employee employee : employeeList) {
            Row row = this.leaves.createRow(numberRows++);

            Cell cell = row.createCell(0);
            cell.setCellValue(employee.getIdEmployee());
            this.leaves.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(employee.getFirstName());
            this.leaves.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(employee.getLastName());
            this.leaves.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(employee.getEmail());
            this.leaves.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(employee.getPhone());
            this.leaves.autoSizeColumn(4);
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(employee.getSex());
            this.leaves.autoSizeColumn(5);
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(employee.getSalary());
            this.leaves.autoSizeColumn(6);
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(employee.getBirthDate());
            this.leaves.autoSizeColumn(7);
            cell.setCellStyle(style);

        }
    }

    public void exportExcel(HttpServletResponse response) throws IOException {
        writeHeadExcel();
        writeDataExcel();
        ServletOutputStream outputStream = response.getOutputStream();
        this.workbook.write(outputStream);
        this.workbook.close();
        outputStream.close();
    }
}
