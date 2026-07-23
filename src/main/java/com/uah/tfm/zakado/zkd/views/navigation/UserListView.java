package com.uah.tfm.zakado.zkd.views.navigation;

import com.uah.tfm.zakado.zkd.backend.data.entity.User;
import com.uah.tfm.zakado.zkd.backend.service.UserService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Usuarios | Zakado IT")
@RolesAllowed("ADMIN")
public class UserListView extends VerticalLayout {

    private static final List<String> ROLES = List.of("ADMIN", "USER");

    private final UserService userService;
    private final AuthenticationContext authContext;
    private final Grid<User> grid = new Grid<>(User.class, false);

    public UserListView(UserService userService, AuthenticationContext authContext) {
        this.userService = userService;
        this.authContext = authContext;

        grid.addColumn(User::getUsername).setHeader("Usuario");
        grid.addComponentColumn(this::createRoleComboBox).setHeader("Rol");
        grid.addComponentColumn(this::createEnabledCheckbox).setHeader("Activo");

        refreshGrid();

        add(grid);
    }

    private boolean esUsuarioActual(User user) {
        return authContext.getAuthenticatedUser(User.class)
                .map(actual -> actual.getUsername().equals(user.getUsername()))
                .orElse(false);
    }

    private ComboBox<String> createRoleComboBox(User user) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(ROLES);
        comboBox.setValue(user.getRole());
        comboBox.setWidth("120px");

        // El admin no puede cambiar su propio rol
        comboBox.setEnabled(!esUsuarioActual(user));

        comboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                return;
            }
            userService.setRole(user.getId(), event.getValue());
            Notification.show("Rol de " + user.getUsername() + " actualizado a " + event.getValue())
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        return comboBox;
    }

    private Checkbox createEnabledCheckbox(User user) {
        Checkbox checkbox = new Checkbox(user.isEnabled());

        // El admin no puede desactivarse a sí mismo
        checkbox.setEnabled(!esUsuarioActual(user));

        checkbox.addValueChangeListener(event -> {
            boolean nuevoEstado = event.getValue();
            userService.setEnabled(user.getId(), nuevoEstado);
            Notification.show(
                    "Usuario " + user.getUsername() + (nuevoEstado ? " activado" : " desactivado"),
                    2000,
                    Notification.Position.TOP_CENTER
            ).addThemeVariants(nuevoEstado ? NotificationVariant.LUMO_SUCCESS : NotificationVariant.LUMO_ERROR);
        });

        return checkbox;
    }

    private void refreshGrid() {
        grid.setItems(userService.findAll());
    }

}
