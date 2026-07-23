package com.uah.tfm.zakado.zkd.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
@PageTitle("Login | Zakado IT")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm loginForm = new LoginForm();
	public LoginView() {
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		loginForm.setAction("login");
		loginForm.addLoginListener(e -> {
			Notification.show("Iniciando sesión...", 1000, Notification.Position.TOP_CENTER);
		});

		add(loginForm);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// Si ya está autenticado, redirigir al dashboard y no mostrar el login
		var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !"anonymousUser".equals(auth.getPrincipal());
		if (isAuthenticated) {
			event.forwardTo(""); // o la ruta de tu DashboardView
			return;
		}

		if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
			loginForm.setError(true);
			Notification.show("Credenciales incorrectas", 3000, Notification.Position.TOP_CENTER);
		}

		if (event.getLocation().getQueryParameters().getParameters().containsKey("logout")) {
			Notification.show("Has cerrado sesión", 3000, Notification.Position.TOP_CENTER);
		}
	}
}