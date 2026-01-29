package com.uah.tfm.zakado.zkd;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "zakadoit")
@PWA(
        name = "Zakado IT",
        shortName = "ZKD",
        offlinePath="offline.html",
        offlineResources = { "images/offline.png" }
)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
