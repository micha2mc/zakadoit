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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class EmployeeForm extends FormLayout {

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DatePicker dob = new DatePicker("Date Of Birth");
    TextArea career = new TextArea();
    RadioButtonGroup<Area> area = new RadioButtonGroup<>("Area");
    ComboBox<Company> company = new ComboBox<>("Company");
    CheckboxGroup<Language> languageCheckboxGroup;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<EmployeeDTO> binder = new BeanValidationBinder<>(EmployeeDTO.class);

    public EmployeeForm(List<Company> companies, List<Area> areas, List<Language> languages) {
        addClassName("employee-form");
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        area.setItems(areas);
        area.setItemLabelGenerator(Area::getName);

        languageCheckboxGroup = new CheckboxGroup<>();
        languageCheckboxGroup.setLabel("Idiomas");
        languageCheckboxGroup.setItems(languages);
        languageCheckboxGroup.setItemLabelGenerator(Language::getName);
        languageCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        binder.forField(languageCheckboxGroup)
                .bind(EmployeeDTO::getLanguages, EmployeeDTO::setLanguages);

        configTextArea();
        add(firstName,
                lastName,
                dob,
                company,
                area,
                career,
                languageCheckboxGroup,
                createButtonsLayout());

    }


    private void configTextArea() {
        int charLimit = 600;
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
                if(Objects.isNull(employee)){
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
        if (employee != null) {
            binder.setBean(employee);

            // Asegurarse de que los idiomas se seleccionen en el checkbox
            if (employee.getLanguages() != null && !employee.getLanguages().isEmpty()) {
                languageCheckboxGroup.setValue(employee.getLanguages());
            } else {
                languageCheckboxGroup.clear();
            }

            // Asegurarse de que los otros campos también se establezcan
            if (employee.getCompany() != null) {
                company.setValue(employee.getCompany());
            }

            if (employee.getArea() != null) {
                area.setValue(employee.getArea());
            }
        } else {
            binder.setBean(null);
            languageCheckboxGroup.clear();
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
