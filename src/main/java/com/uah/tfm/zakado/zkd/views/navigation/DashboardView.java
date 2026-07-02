package com.uah.tfm.zakado.zkd.views.navigation;

import com.uah.tfm.zakado.zkd.backend.service.EmployeeService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Dashboard | Zakado IT")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {



    private final EmployeeService employeeService;

    public DashboardView(final EmployeeService employeeService) {
        this.employeeService = employeeService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);


        add(getAllEmployees());
    }


    private Component getAllEmployees() {

        Span stats = new Span(employeeService.findAllEmployees("").size() + " employees");
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }
}
