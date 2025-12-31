package com.uah.tfm.zakado.zkd.exception;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(EmployeeEmptyException.class)
    public void handleEmployeeEmptyException(EmployeeEmptyException ex) {
        Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
