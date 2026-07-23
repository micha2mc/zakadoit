package com.uah.tfm.zakado.zkd.views;

import com.uah.tfm.zakado.zkd.backend.data.entity.User;
import com.uah.tfm.zakado.zkd.views.employee.EmployeeView;
import com.uah.tfm.zakado.zkd.views.navigation.DashboardView;
import com.uah.tfm.zakado.zkd.views.navigation.DownloadReportsView;
import com.uah.tfm.zakado.zkd.views.navigation.UserListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;

@AnonymousAllowed
public class MainLayout extends AppLayout {

    private final transient AuthenticationContext authContext;

    public MainLayout(AuthenticationContext authContext) {
        this.authContext = authContext;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("ZAKADO IT");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        HorizontalLayout header = authContext.getAuthenticatedUser(User.class)
                .map(user -> {
                    Button logoutButton = new Button("Logout", click -> authContext.logout());
                    Span loggedUser = new Span("Usuario: " + user.getUsername());
                    return new HorizontalLayout(
                            new DrawerToggle(),
                            logo,
                            new HorizontalLayout(loggedUser, logoutButton)
                    );
                })
                .orElseGet(() -> new HorizontalLayout(new DrawerToggle(), logo));

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
        addToNavbar(header);
    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Employees", EmployeeView.class),
                new RouterLink("Dashboard", DashboardView.class),
                new RouterLink("Reports", DownloadReportsView.class),
                new RouterLink("Userlist", UserListView.class)
        ));
    }
}
