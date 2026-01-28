package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.entity.Language;
import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.uah.tfm.zakado.zkd.exception.EmployeeEmptyException;
import com.uah.tfm.zakado.zkd.service.EmployeeService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Objects;

@SpringComponent
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class EmployeeView extends VerticalLayout {

    private Dialog dialog;
    private EmployeeForm form;
    private final TextField filterText = new TextField();
    private final Grid<EmployeeDTO> grid = new Grid<>(EmployeeDTO.class);

    private EmployeeService employeeService;


    public EmployeeView(final EmployeeService employeeService) {
        this.employeeService = employeeService;
        addClassName("employee-view");
        setSizeFull();
        configureGrid();
        configureDialog();
        add(getToolbar(), grid);
        updateList();
    }

    /**
     * Configuración del popup con el formulario
     */

    private void configureDialog() {
        dialog = new Dialog();
        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);

        // Crear el formulario
        createAndRefreshForm();
        dialog.addDialogCloseActionListener(e -> {
            clearForm();
            dialog.close();
        });
    }

    private void clearForm() {
        if (form != null) {
            EmployeeDTO emptyEmployee = new EmployeeDTO();
            form.setEmployee(emptyEmployee);
            form.getBinder().readBean(emptyEmployee);
        }
    }

    private void createAndRefreshForm() {
        if (Objects.nonNull(form)) {
            dialog.remove(form);
        }

        // Crear nuevo formulario
        form = new EmployeeForm(
                employeeService.findAllCompanies(),
                employeeService.findAllArea(),
                employeeService.findAllLanguages()
        );

        // Configuración de los listeners de los botones
        form.addSaveListener(event -> {
            saveEmployee(event.getEmployee());
        });

        form.addDeleteListener(event -> {
            deleteEmployee(event.getEmployee());
        });

        form.addCloseListener(event -> {
            clearForm();
            dialog.close();
        });
        dialog.add(form);
        dialog.setWidth("600px");
        dialog.setHeight("auto");
    }


    private void updateList() {
        grid.setItems(employeeService.findAllEmployees(filterText.getValue()));
    }


    /**
     * Metodo separado para mostrar confirmación de eliminación
     */
    private void showDeleteConfirmation(EmployeeDTO employee) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Delete Employee");
        confirmDialog.setText("Are you sure you want to delete " +
                employee.getFirstName() + " " + employee.getLastName() + "?");
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText("Delete");
        confirmDialog.setConfirmButtonTheme("error primary");
        confirmDialog.open();
        confirmDialog.addConfirmListener(event -> {
            try {
                employeeService.deleteEmployee(employee);
                updateList();
                Notification.show("Employee deleted successfully",
                                1000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception e) {
                String message = "Error deleting employee";
                log.error(message + ": " + e.getMessage());
                throw new EmployeeEmptyException(message);
            }
        });
    }

    private void deleteEmployee(EmployeeDTO employee) {
        if (Objects.nonNull(employee.getId())) {
            showDeleteConfirmation(employee);
            dialog.close();
        } else {
            dialog.close();
            String message = "You're trying to delete an empty employee";
            Notification.show(message, 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void saveEmployee(EmployeeDTO employee) {
        try {
            employeeService.saveEmployee(employee);
            updateList();
            dialog.close();
            Notification.show("Employee saved successfully",
                            1000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            String message = "Error saving employee";
            log.error("{}: {}", message, e.getMessage());
            throw new EmployeeEmptyException(message);
        }
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name or CK...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.addClickListener(click -> addEmployee());
        addEmployeeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var toolbar = new HorizontalLayout(filterText, addEmployeeButton);
        toolbar.addClassName("toolbar");
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        return toolbar;
    }

    private void addEmployee() {
        clearForm();
        dialog.open();
    }

    private void editEmployee(final EmployeeDTO employee) {
        form.setEmployee(employee);
        dialog.open();
    }

    /**
     * Configuración del grid
     */
    private void configureGrid() {
        grid.addClassNames("employee-grid");
        grid.setSizeFull();
        grid.setColumns("corporateKey", "firstName", "lastName");
        grid.addColumn(employee -> employee.getArea().getName()).setHeader("Area")
                .setSortable(Boolean.TRUE);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                showEmployeeCard(event.getValue());
                grid.select(null);
            }
        });
    }

    /**
     * Crea y muestra la tarjeta del empleado
     */
    private void showEmployeeCard(EmployeeDTO employee) {
        EmployeeDTO fullEmployee = employeeService.getEmployeeWithRelations(employee.getId());
        List<Language> allLanguages = employeeService.findAllLanguages();
        // Crear un nuevo diálogo para la tarjeta
        Dialog cardDialog = new Dialog();
        cardDialog.setDraggable(true);
        cardDialog.setResizable(false);
        cardDialog.setCloseOnEsc(true);
        cardDialog.setCloseOnOutsideClick(true);

        // Creación de Card
        EmployeeCard employeeCard = new EmployeeCard(fullEmployee, allLanguages);

        //Botones de la Card
        Button editButton = new Button("Edit", VaadinIcon.EDIT.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(e -> {
            cardDialog.close();
            editEmployee(fullEmployee);
        });

        Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(e -> {
            cardDialog.close();
            showDeleteConfirmation(fullEmployee);
        });

        Button cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());
        cancelButton.addClickListener(e -> cardDialog.close());

        // Crear layout para los botones
        HorizontalLayout buttonsLayout = new HorizontalLayout(editButton, deleteButton, cancelButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Layout principal con la tarjeta y botones
        VerticalLayout cardLayout = new VerticalLayout(employeeCard, buttonsLayout);
        cardLayout.setSpacing(true);
        cardLayout.setPadding(true);
        cardLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        cardDialog.add(cardLayout);
        cardDialog.setWidth("500px");
        cardDialog.setHeight("auto");
        cardDialog.open();
    }

    private Icon getEnvelopeIcon() {
        Icon emailIcon = VaadinIcon.ENVELOPE.create();
        emailIcon.setColor("var(--lumo-primary-color)");
        emailIcon.setSize("16px");
        return emailIcon;
    }
}
