package com.uah.tfm.zakado.zkd.views.utils;

import com.uah.tfm.zakado.zkd.backend.data.mapper.dto.EmployeeDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;

public class ServiceUtils {

    private ServiceUtils(){}

    public static Component createExperienceLevelBadge(final EmployeeDTO employeeDTO) {
        int years = employeeDTO.getYearOfExperience();
        Span badge = new Span();
        if (years <= 3) {
            badge.setText("Junior");
            badge.addClassName("junior-badge");
            badge.getStyle()
                    .set("background-color", "#e8f5e9")
                    .set("color", "#2e7d32")
                    .set("padding", "4px 8px")
                    .set("border-radius", "4px")
                    .set("font-weight", "500");
        } else if (years <= 8) {
            badge.setText("Senior");
            badge.addClassName("senior-badge");
            badge.getStyle()
                    .set("background-color", "#e3f2fd")
                    .set("color", "#1565c0")
                    .set("padding", "4px 8px")
                    .set("border-radius", "4px")
                    .set("font-weight", "500");
        } else {
            badge.setText("Analista");
            badge.addClassName("analyst-badge");
            badge.getStyle()
                    .set("background-color", "#f3e5f5")
                    .set("color", "#6a1b9a")
                    .set("padding", "4px 8px")
                    .set("border-radius", "4px")
                    .set("font-weight", "500");
        }

        return badge;
    }
}
