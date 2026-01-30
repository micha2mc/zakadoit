package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.backend.data.entity.Language;
import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeCard extends VerticalLayout {

    private final EmployeeDTO employeeDTO;
    private final List<Language> allLanguages;

    public EmployeeCard(EmployeeDTO employeeDTO, List<Language> allLanguages) {
        this.employeeDTO = employeeDTO;
        this.allLanguages = allLanguages;
        addClassName("employee-detail-card");
        setPadding(true);
        setSpacing(true);
        setAlignItems(FlexComponent.Alignment.CENTER);

        createCardContent();
    }

    private void createCardContent() {
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
        avatar.setImage("/images/men.png");
        avatar.addClassName("employee-avatar");
        avatar.setThemeName("large");

        H2 name = new H2(employeeDTO.getFullName());
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
        if (Objects.nonNull(employeeDTO.getLanguages())) {
            content.add(createLanguagesSection(employeeDTO.getLanguages()));
        } else {
            content.add(createNoLanguagesMessage());
        }
        if (StringUtils.hasText(employeeDTO.getCareer())) {
            content.add(createCareerSection(employeeDTO.getCareer()));
        }

        return content;
    }

    private Component createLanguagesSection(final Set<Language> languages) {
        // Convertir Set a List para ordenarlos alfabéticamente
        List<Language> sortedAllLanguages = this.allLanguages.stream()
                .sorted(Comparator.comparing(Language::getName))
                .collect(Collectors.toList());
        Set<Long> employeeLanguageIds = Objects.nonNull(languages) ?
                languages.stream()
                        .map(Language::getId)
                        .collect(Collectors.toSet()) :
                Collections.emptySet();

        Grid<Language> grid = new Grid<>();
        grid.addClassName("languages-grid");
        grid.setItems(sortedAllLanguages);
        grid.setAllRowsVisible(true);
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);

        // Columna de nombre del idioma
        grid.addColumn(new ComponentRenderer<>(language -> {
                    Span name = new Span(language.getName());
                    name.addClassName("language-name");
                    return name;
                }))
                .setHeader(createLanguagesHeader())
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Columna de código ISO
        grid.addColumn(new ComponentRenderer<>(language -> {
                    Span code = new Span(language.getIsocode());
                    code.addClassName("language-iso-code");
                    return code;
                })).setHeader(createIsoHeader())
                .setWidth("80px")
                .setTextAlign(ColumnTextAlign.CENTER);

        // Columna con icono de verificación (opcional)
        grid.addColumn(new ComponentRenderer<>(language -> {
                    boolean hasLanguage = employeeLanguageIds.contains(language.getId());
                    Icon statusIcon;
                    if (hasLanguage) {
                        statusIcon = VaadinIcon.CHECK.create();
                        statusIcon.addClassName("language-status-icon-check");

                    } else {
                        statusIcon = VaadinIcon.CLOSE_SMALL.create();
                        statusIcon.addClassName("language-status-icon-close");
                    }
                    statusIcon.setSize("var(--lumo-icon-size-s)");


                    HorizontalLayout layout = new HorizontalLayout(statusIcon);
                    layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
                    layout.setAlignItems(FlexComponent.Alignment.CENTER);
                    return layout;
                })).setHeader("")
                .setWidth("60px")
                .setFlexGrow(0);

        return grid;
    }

    private Component createIsoHeader() {
        Div headerContainer = new Div();
        headerContainer.addClassName("languages-grid-header-layout-center");

        Icon icon = VaadinIcon.FLAG.create();
        icon.addClassName("languages-grid-header-icon");
        icon.setSize("14px");

        Span text = new Span("ISO");
        text.addClassName("languages-grid-header-text");

        headerContainer.add(icon, text);
        return headerContainer;
    }

    private Component createLanguagesHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.addClassName("languages-grid-header-layout");

        Icon icon = VaadinIcon.GLOBE.create();
        icon.addClassName("languages-grid-header-icon");
        icon.setSize("14px");

        Span text = new Span("Languages");
        text.addClassName("languages-grid-header-text");

        headerLayout.add(icon, text);
        return headerLayout;
    }

    private Component createNoLanguagesMessage() {
        HorizontalLayout messageLayout = new HorizontalLayout(
                VaadinIcon.GLOBE.create(),
                new Span("No registra idiomas")
        );
        messageLayout.setSpacing(true);
        messageLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        messageLayout.getStyle()
                .set("color", "var(--lumo-disabled-text-color)")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("font-style", "italic")
                .set("margin-top", "var(--lumo-space-s)");

        return messageLayout;
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
                .set("font-weight", "500")
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

    /*private String getInitials() {
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
    }*/

    public EmployeeDTO getEmployee() {
        return employeeDTO;
    }

}