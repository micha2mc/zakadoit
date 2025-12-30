package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.mapper.dto.EmployeeDTO;
import com.uah.tfm.zakado.zkd.service.EmployeeService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
public class EmployeeView extends VerticalLayout {

    Grid<EmployeeDTO> grid = new Grid<>(EmployeeDTO.class);
    TextField filterText = new TextField();
    EmployeeForm form;
    EmployeeService employeeService;


    public EmployeeView(final EmployeeService employeeService) {
        this.employeeService = employeeService;
        addClassName("employee-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(employeeService.findAllEmployees(filterText.getValue()));
    }

    private void deleteEmployee(EmployeeForm.DeleteEvent event) {
        employeeService.deleteEmployee(event.getEmployee());
        updateList();
        closeEditor();
    }

    private void saveEmployee(EmployeeForm.SaveEvent event) {
        employeeService.saveEmployee(event.getEmployee());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name or CK...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Employee");
        addContactButton.addClickListener(click -> addEmployee());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addEmployee() {
        grid.asSingleSelect().clear();
        editEmployee(new EmployeeDTO());
    }

    private void editEmployee(final EmployeeDTO employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setEmployee(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new EmployeeForm(employeeService.findAllCompanies(), employeeService.findAllArea());
        form.setWidth("25em");
        form.addSaveListener(this::saveEmployee); // <1>
        form.addDeleteListener(this::deleteEmployee); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

    private void configureGrid() {
        grid.addClassNames("employee-grid");
        grid.setSizeFull();
        grid.setColumns("corporateKey","firstName", "lastName", "dob");
        grid.addColumn(EmployeeDTO::getEmail)
                        .setHeader(getEnvelopeIcon());
        grid.addColumn(employee -> employee.getArea().getName()).setHeader("Area")
                .setSortable(Boolean.TRUE);
        grid.addColumn(employee -> employee.getCompany().getName()).setHeader("Company")
                .setSortable(Boolean.TRUE);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editEmployee(event.getValue()));
    }


    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private Icon getEnvelopeIcon() {
        Icon emailIcon = VaadinIcon.ENVELOPE.create();
        emailIcon.setColor("var(--lumo-primary-color)");
        emailIcon.setSize("16px");
        return emailIcon;
    }
}
