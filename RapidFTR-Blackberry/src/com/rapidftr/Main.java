package com.rapidftr;

import com.rapidftr.services.LoginService;
import com.rapidftr.services.impl.LoginServiceImpl;
import com.rapidftr.utilities.HttpServer;
import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
import net.rim.device.api.ui.UiApplication;

import com.rapidftr.controllers.LoginController;
import com.rapidftr.screens.LoginScreen;
import com.rapidftr.screens.UiStack;
import com.rapidftr.utilities.SettingsStore;

public class Main extends UiApplication {

	public static final String APPLICATION_NAME = "Rapid FTR";

	/**
	 * Entry point for application.
	 */
	public static void main(String[] args) {

		
		// Create a new instance of the application.
		Main application = new Main();

		// To make the application enter the event thread and start processing
		// messages,
		// we invoke the enterEventDispatcher() method.
		application.enterEventDispatcher();
	}

	/**
	 * <p>
	 * The default constructor. Creates all of the RIM UI components and pushes
	 * the application's root screen onto the UI stack.
	 */
	public Main() {
		enableEventInjection();

		SettingsStore settings = new SettingsStore();
        LoginService loginService = new LoginServiceImpl(HttpServer.getInstance());
        LoginController loginController = new LoginController(new LoginScreen(settings), new UiStack(this), loginService, settings);
		loginController.show();
	
	}

	private void enableEventInjection() {
		ApplicationPermissionsManager manager = ApplicationPermissionsManager
				.getInstance();

		int permission = ApplicationPermissions.PERMISSION_EVENT_INJECTOR;

		if (manager.getApplicationPermissions().getPermission(permission) == ApplicationPermissions.VALUE_ALLOW) {
			System.out.println("Got correct permissions");
			return;
		}

		ApplicationPermissions requestedPermissions = new ApplicationPermissions();

		if (!requestedPermissions.containsPermissionKey(permission)) {
			requestedPermissions.addPermission(permission);
		}

		ApplicationPermissionsManager.getInstance().invokePermissionsRequest(
				requestedPermissions);
	}
}
