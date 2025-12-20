package com.uah.tfm.zakado.zkd.views.employee;

import com.uah.tfm.zakado.zkd.data.entity.Employee;
import com.uah.tfm.zakado.zkd.service.AreaService;
import com.uah.tfm.zakado.zkd.service.CompanyService;
import com.uah.tfm.zakado.zkd.service.EmployeeService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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

    Grid<Employee> grid = new Grid<>(Employee.class);
    TextField filterText = new TextField();
    EmployeeForm form;
    CompanyService companyService;
    EmployeeService employeeService;
    AreaService areaService;


    public EmployeeView(final CompanyService companyService, final EmployeeService employeeService,
                        final AreaService areaService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.areaService = areaService;
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

    private void saveContact(EmployeeForm.SaveEvent event) {
        employeeService.saveEmployee(event.getEmployee());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
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
        editEmployee(new Employee());
    }

    private void editEmployee(final Employee employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setEmployee(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new EmployeeForm(companyService.findAllCompanies(), areaService.findAllArea());
        form.setWidth("25em");
        form.addSaveListener(this::saveContact); // <1>
        form.addDeleteListener(this::deleteEmployee); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

    private void configureGrid() {
        grid.addClassNames("employee-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(employee -> employee.getArea().getName()).setHeader("Area");
        grid.addColumn(employee -> employee.getCompany().getName()).setHeader("Company");
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
}
