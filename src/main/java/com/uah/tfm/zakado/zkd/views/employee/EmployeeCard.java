package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;

public class EmployeeCard extends VerticalLayout {

    private final EmployeeDTO employee;

    public EmployeeCard(EmployeeDTO employeeDTO) {
        this.employee = employeeDTO;
        addClassName("employee-detail-card");
        setPadding(true);
        setSpacing(true);
        //setWidth("400px");
        setAlignItems(FlexComponent.Alignment.CENTER);

        createCardContent();
    }

    private void createCardContent() {
        // Header con avatar y nombre
        VerticalLayout headerLayout = createHeader();
        VerticalLayout content = createContent();
        add(headerLayout, content);
    }

    private VerticalLayout createHeader() {
        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.setSpacing(false);
        headerLayout.setPadding(false);

        // Avatar con iniciales
        Avatar avatar = new Avatar();
        avatar.setAbbreviation(getInitials());
        avatar.addClassName("employee-avatar");
        avatar.setThemeName("large");

        // Nombre completo
        H2 name = new H2(employee.getFirstName() + " " + employee.getLastName());
        name.addClassName("employee-full-name");

        // Corporate Key
        Span corporateKey = new Span("CK: " + employee.getCorporateKey());
        corporateKey.addClassName("employee-corporate-key");

        headerLayout.add(avatar, name, corporateKey);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return headerLayout;
    }

    private VerticalLayout createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setPadding(false);

        // Email
        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
            content.add(createInfoRow(VaadinIcon.ENVELOPE, employee.getEmail()));
        }

        // Fecha de nacimiento
        if (employee.getDob() != null) {
            String formattedDob = employee.getDob()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            content.add(createInfoRow(VaadinIcon.CALENDAR, formattedDob));
        }

        // Área
        if (employee.getArea() != null && employee.getArea().getName() != null) {
            content.add(createInfoRow(VaadinIcon.BUILDING, employee.getArea().getName()));
        }

        // Compañía
        if (employee.getCompany() != null && employee.getCompany().getName() != null) {
            content.add(createInfoRow(VaadinIcon.OFFICE, employee.getCompany().getName()));
        }

        return content;
    }

    private HorizontalLayout createInfoRow(VaadinIcon icon, String text) {
        HorizontalLayout row = new HorizontalLayout(
                icon.create(),
                new Span(text)
        );
        row.setSpacing(true);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setWidthFull();
        row.addClassName("employee-info-row");
        return row;
    }

    private String getInitials() {
        String initials = "";

        if (!employee.getFirstName().isBlank()) {
            String[] parts = employee.getFirstName().split(" ");
            if (parts.length >= 2) {
                initials = parts[0].charAt(0) + String.valueOf(parts[1].charAt(0));
            } else {
                initials += employee.getFirstName().charAt(0) + employee.getLastName().charAt(0);
            }

        }
        return initials.isEmpty() ? "?" : initials;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

}