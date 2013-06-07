package de.tilliwilli.phantasien.app.modules;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.annotations.Controller;

import de.tilliwilli.phantasien.web.controllers.TestController;

/**
 * {@link MvcModule} that sets up all our {@link Controller controllers} for request processing and
 * other LimeMVC related stuff.
 */
public class ControllerModule extends MvcModule {

	@Override
	protected void configureControllers() {

		// forward all other requests to the appropriate version under /user
		//serve("/*").with(UserForwardingServlet.class);
		control("/test/*").withController(TestController.class);
	}
}
