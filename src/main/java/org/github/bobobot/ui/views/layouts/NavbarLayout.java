package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.LoginView;
import org.github.bobobot.ui.views.MainView;
import org.github.bobobot.ui.views.RegisterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NavbarLayout extends HorizontalLayout implements View {

	@Autowired
	IUserService userService;
	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	void init() {
		log.info("rendered navbar");
		render();
	}

	public void render() {
		removeAllComponents();
		setWidthFull();

		addStyleName("navbar");

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addStyleName("navbar-button-layout");

		Image logo = new Image(null, new ThemeResource("images/logo.png"));
		logo.addStyleName("logo");
		logo.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(MainView.name));
		addComponent(logo);
		Button loginButton = new Button("Login", event -> getUI().getNavigator().navigateTo(LoginView.name));
		loginButton.addStyleName("btn btn-primary");
		Button registerButton = new Button("Register", event -> getUI().getNavigator().navigateTo(RegisterView.name));
		registerButton.addStyleName("btn btn-primary");
		registerButton.removeStyleName("v-button");
		Button logoutButton = new Button("Log out", event -> {
			PermissionHandler.setCurrentUserToNotLoggedIn();
			Page.getCurrent().reload();
		});
		logoutButton.addStyleName("btn btn-primary");
		buttonLayout.addComponent(loginButton);
		buttonLayout.addComponent(registerButton);

		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);

		//TODO: only for debug purposes, delete later
//		Button adminLogin = new Button("Login as admin", event -> {
//			userService.login("admin@chan.com", "admin");
//			init();
//		});
//		buttonLayout.addComponent(adminLogin);
//		PermissionHandler.restrictComponentToLoggedOutUsers(adminLogin, adminLogin::setVisible);
		//TODO: end of TODO

		if (PermissionHandler.isLoggedIn()) {
			NotificationLayout notifLayout = appContext.getBean(NotificationLayout.class);
			PopupView notificationPopUp = new PopupView(null, notifLayout);

			Button notificationButton = new Button();
			notificationButton.addStyleNames("notification-button", "p-0");
			notificationButton.setIcon(new ThemeResource("images/notification.png"));
			notificationButton.setWidth(37, Unit.PIXELS);
			notificationButton.setHeight(37, Unit.PIXELS);

			notificationButton.addClickListener(event -> notificationPopUp.setPopupVisible(true));

			buttonLayout.addComponents(notificationPopUp, notificationButton);
			PermissionHandler.restrictComponentToLoggedInUsers(notificationPopUp, notificationPopUp::setVisible);
		}

		buttonLayout.addComponent(logoutButton);

		PermissionHandler.restrictComponentToLoggedOutUsers(loginButton, loginButton::setVisible);
		PermissionHandler.restrictComponentToLoggedOutUsers(registerButton, registerButton::setVisible);
		PermissionHandler.restrictComponentToLoggedInUsers(logoutButton, logoutButton::setVisible);
	}
}
