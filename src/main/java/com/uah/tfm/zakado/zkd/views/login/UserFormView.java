package com.uah.tfm.zakado.zkd.views.login;

import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.UserFormData;
import com.uah.tfm.zakado.zkd.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("users/new")
@PageTitle("Nuevo usuario | Zakado IT")
@RolesAllowed("ADMIN")
public class UserFormView extends VerticalLayout {
    private final UserService userService;
    private final BeanValidationBinder<UserFormData> binder = new BeanValidationBinder<>(UserFormData.class);

    private final TextField username = new TextField("Usuario");
    private final PasswordField password = new PasswordField("Contraseña");
    private final EmailField email = new EmailField("Email");

    public UserFormView(UserService userService) {
        this.userService = userService;

        FormLayout formLayout = new FormLayout(username, password, email);

        Button saveButton = new Button("Guardar", event -> guardar());
        saveButton.getElement().getThemeList().add("primary");

        binder.bindInstanceFields(this);

        add(formLayout, saveButton);
    }

    private void guardar() {
        UserFormData formData = new UserFormData();
        if (binder.writeBeanIfValid(formData)) {
            try {
                userService.createUser(formData.getUsername(), formData.getPassword(), formData.getEmail());
                Notification.show("Usuario creado correctamente (rol USER, activo)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                binder.readBean(new UserFormData());
            } catch (IllegalArgumentException e) {
                Notification.show(e.getMessage())
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }
    }

}
