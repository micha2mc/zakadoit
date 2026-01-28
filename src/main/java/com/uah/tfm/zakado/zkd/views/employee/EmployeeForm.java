package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.entity.Area;
import com.uah.tfm.zakado.zkd.data.entity.Company;
import com.uah.tfm.zakado.zkd.data.entity.Language;
import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class EmployeeForm extends FormLayout {

    TextArea career;
    RadioButtonGroup<Area> area;
    ComboBox<Company> company;
    CheckboxGroup<Language> languageCheckboxGroup;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DatePicker dob = new DatePicker("Date Of Birth");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<EmployeeDTO> binder = new BeanValidationBinder<>(EmployeeDTO.class);

    public EmployeeForm(List<Company> companies, List<Area> areas, List<Language> languages) {
        addClassName("employee-form");

        configCombobox(companies);
        configRadioButtonGroup(areas);
        configCheckboxGroup(languages);
        configTextArea();

        binder.forField(company).bind(EmployeeDTO::getCompany, EmployeeDTO::setCompany);
        binder.forField(area).bind(EmployeeDTO::getArea, EmployeeDTO::setArea);
        binder.forField(languageCheckboxGroup)
                .bind(EmployeeDTO::getLanguages, EmployeeDTO::setLanguages);

        binder.bindInstanceFields(this);


        add(firstName, lastName, dob, company, area, career, languageCheckboxGroup, createButtonsLayout());

    }

    private void configCombobox(List<Company> companies) {
        company = new ComboBox<>("Company");
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
    }

    private void configCheckboxGroup(List<Language> languages) {
        languageCheckboxGroup = new CheckboxGroup<>();
        languageCheckboxGroup.setLabel("Languages");
        languageCheckboxGroup.setItems(languages);
        languageCheckboxGroup.setItemLabelGenerator(Language::getName);
        languageCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }


    private void configRadioButtonGroup(final List<Area> areas) {
        area = new RadioButtonGroup<>("Area");
        area.setItems(areas);
        area.setItemLabelGenerator(Area::getName);
        area.setRenderer(new ComponentRenderer<>(area -> {
            Span name = new Span(area.getName());
            name.getStyle().set("margin-left", "10px");
            return name;
        }));
    }

    private void configTextArea() {
        int charLimit = 600;
        career = new TextArea();
        career.setWidthFull();
        career.setMinHeight("100px");
        career.setMaxHeight("150px");
        career.setLabel("Career");
        career.setMaxLength(charLimit);
        career.setValueChangeMode(ValueChangeMode.EAGER);
        career.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            try {
                EmployeeDTO employee = binder.getBean();
                if (Objects.isNull(employee)) {
                    employee = new EmployeeDTO();
                }
                binder.writeBean(employee);
                // Verificar que los datos se hayan escrito correctamente
                if (employee.getLanguages() == null || employee.getLanguages().isEmpty()) {
                    // Si los idiomas están vacíos, asignar los del checkbox
                    employee.setLanguages(languageCheckboxGroup.getSelectedItems());
                }
                fireEvent(new SaveEvent(this, employee));
            } catch (ValidationException e) {
                // Manejar error de validación
                Notification.show("Error en la validación: " + e.getMessage(),
                        3000, Notification.Position.MIDDLE);
            }
        }
    }


    public void setEmployee(EmployeeDTO employee) {
        binder.setBean(employee);
        if (employee.getArea() != null) {
            Area areaToSelect = area.getListDataView()
                    .getItems()
                    .filter(a -> a.getId().equals(employee.getArea().getId()))
                    .findFirst()
                    .orElse(null);
            area.setValue(areaToSelect);
        } else {
            area.clear();
        }

        if (employee.getLanguages() != null && !employee.getLanguages().isEmpty()) {
            Set<Language> languagesToSelect = languageCheckboxGroup.getListDataView()
                    .getItems()
                    .filter(lang -> employee.getLanguages().stream()
                            .anyMatch(empLang -> empLang.getId().equals(lang.getId())))
                    .collect(Collectors.toSet());
            languageCheckboxGroup.setValue(languagesToSelect);
        } else {
            languageCheckboxGroup.clear();
        }

        if (employee.getCompany() != null) {
            Company companyToSelect = company.getListDataView()
                    .getItems()
                    .filter(c -> c.getId().equals(employee.getCompany().getId()))
                    .findFirst()
                    .orElse(null);
            company.setValue(companyToSelect);
        } else {
            company.clear();
        }

    }

    // Events
    @Getter
    public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeForm> {
        private final EmployeeDTO employee;

        protected EmployeeFormEvent(EmployeeForm source, EmployeeDTO employee) {
            super(source, false);
            this.employee = employee;
        }

    }

    public static class SaveEvent extends EmployeeFormEvent {
        SaveEvent(EmployeeForm source, EmployeeDTO employee) {
            super(source, employee);
        }
    }

    public static class DeleteEvent extends EmployeeFormEvent {
        DeleteEvent(EmployeeForm source, EmployeeDTO employee) {
            super(source, employee);
        }

    }

    public static class CloseEvent extends EmployeeFormEvent {
        CloseEvent(EmployeeForm source) {
            super(source, null);
        }
    }

    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        addListener(DeleteEvent.class, listener);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }
}
