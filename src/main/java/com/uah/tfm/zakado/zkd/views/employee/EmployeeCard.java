package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EmployeeCard extends VerticalLayout {

    private final EmployeeDTO employeeDTO;

    public EmployeeCard(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
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

        H2 name = new H2(employeeDTO.getFirstName() + " " + employeeDTO.getLastName());
        name.addClassName("employee-full-name");
        Span corporateKey = new Span("CK: " + employeeDTO.getCorporateKey());
        corporateKey.addClassName("employee-corporate-key");

        headerLayout.add(avatar, name, corporateKey);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return headerLayout;
    }

    private VerticalLayout createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setPadding(false);
        if (StringUtils.hasText(employeeDTO.getEmail())) {
            content.add(createInfoRow(VaadinIcon.ENVELOPE, employeeDTO.getEmail()));
        }
        if (employeeDTO.getDob() != null) {
            String formattedDob = employeeDTO.getDob()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            content.add(createInfoRow(VaadinIcon.CALENDAR, formattedDob));
        }
        if (Objects.nonNull(employeeDTO.getArea()) && employeeDTO.getArea().getName() != null) {
            content.add(createInfoRow(VaadinIcon.BUILDING, employeeDTO.getArea().getName()));
        }
        if (Objects.nonNull(employeeDTO.getCompany()) && employeeDTO.getCompany().getName() != null) {
            content.add(createInfoRow(VaadinIcon.OFFICE, employeeDTO.getCompany().getName()));
        }
        if(StringUtils.hasText(employeeDTO.getCareer())){
            content.add(createCareerSection(employeeDTO.getCareer()));
        }

        return content;
    }

    private Component createCareerSection(final String career) {
        VerticalLayout careerLayout = new VerticalLayout();
        careerLayout.setSpacing(false);
        careerLayout.setPadding(false);
        careerLayout.setWidthFull();

        // Espacio visual para separar la sección
        careerLayout.getStyle()
                .set("margin-top", "15px")
                .set("padding-top", "10px")
                .set("border-top", "1px solid var(--lumo-contrast-10pct)");

        // Label
        H4 label = new H4("Career:");
        label.getStyle()
                .set("margin", "0 0 8px 0")
                .set("color", "var(--lumo-primary-text-color)")
                .set("font-size", "var(--lumo-font-size-m)");

        Div careerContent = new Div();
        careerContent.setText(career);
        careerContent.getStyle()
                .set("max-height", "150px")
                .set("overflow-y", "auto")
                .set("padding", "8px")
                .set("background", "var(--lumo-contrast-5pct)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("line-height", "1.4")
                .set("white-space", "pre-wrap");

        careerLayout.add(label, careerContent);

        return careerLayout;
    }

    private HorizontalLayout createInfoRow(VaadinIcon icon, String text) {
        HorizontalLayout row = new HorizontalLayout(
                icon.create(),
                new Span(text));
        row.setSpacing(true);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setWidthFull();
        row.addClassName("employee-info-row");
        return row;
    }

    private String getInitials() {
        String initials = "";

        if (StringUtils.hasText(employeeDTO.getFirstName())) {
            String[] parts = employeeDTO.getFirstName().split(" ");
            if (parts.length >= 2) {
                initials = parts[0].charAt(0) + String.valueOf(parts[1].charAt(0));
            } else {
                initials = employeeDTO.getFirstName().charAt(0) + String.valueOf(employeeDTO.getLastName().charAt(0));
            }

        }
        return initials.isEmpty() ? "?" : initials;
    }

    public EmployeeDTO getEmployee() {
        return employeeDTO;
    }

}