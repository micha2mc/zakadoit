package com.uah.tfm.zakado.zkd.views.utils;

import com.uah.tfm.zakado.zkd.backend.service.EmployeeService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayOutputStream;

@PermitAll
@Route(value = "reports", layout = MainLayout.class)
@PageTitle("Reports | ZAKADO IT")
public class DownloadReports extends VerticalLayout {

    private final EmployeeService employeeService;

    public DownloadReports(final EmployeeService employeeService) {
        this.employeeService = employeeService;
        addClassName("reports-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Button excelButton = new Button("Descargar Excel",
                e -> downloadFile());

        add(excelButton);
    }

    private void downloadFile() {
        byte[] excelContent = generateExcelContent();

        UI.getCurrent().getPage().executeJs("""
                        const blob = new Blob([$0], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
                        const url = URL.createObjectURL(blob);
                        const a = document.createElement('a');
                        a.href = url;
                        a.download = 'Reporte_Employees_zkdit.xlsx';
                        document.body.appendChild(a);
                        a.click();
                        document.body.removeChild(a);
                        URL.revokeObjectURL(url);
                        """,
                excelContent
        );
    }

    private byte[] generateExcelContent() {
        employeeService.findAllEmployees("");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Usar Apache POI
            // Workbook workbook = new XSSFWorkbook();
            // workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

