package com.uah.tfm.zakado.zkd.views.login;

import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.UserFormData;
import com.uah.tfm.zakado.zkd.backend.service.UserService;
import com.uah.tfm.zakado.zkd.views.MainLayout;
import com.uah.tfm.zakado.zkd.views.navigation.UserListView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "users/new", layout = MainLayout.class)
@PageTitle("New User | Zakado IT")
@RolesAllowed("ADMIN")
public class UserFormView extends VerticalLayout {

    private final UserService userService;
    private final BeanValidationBinder<UserFormData> binder = new BeanValidationBinder<>(UserFormData.class);

    private final TextField username = new TextField("User");
    private final PasswordField password = new PasswordField("Password");
    private final EmailField email = new EmailField("Email");

    public UserFormView(UserService userService) {
        this.userService = userService;

        FormLayout formLayout = new FormLayout(username, password, email);
        formLayout.setWidth("400px");
        formLayout.setMaxWidth("90vw");

        Button saveButton = new Button("Save", event -> saveUser());
        saveButton.getElement().getThemeList().add("primary");

        Button cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate(UserListView.class));
        });

        binder.bindInstanceFields(this);

        add(formLayout, new HorizontalLayout(saveButton, cancelButton));
    }

    private void saveUser() {
        UserFormData formData = new UserFormData();
        if (binder.writeBeanIfValid(formData)) {
            try {
                userService.createUser(formData.getUsername(), formData.getPassword(), formData.getEmail());
                Notification.show("User created successfully (role: USER, active)")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                getUI().ifPresent(ui -> ui.navigate(UserListView.class));
            } catch (IllegalArgumentException e) {
                Notification.show(e.getMessage())
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }
    }

}
