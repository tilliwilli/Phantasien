package de.tilliwilli.phantasien.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import de.tilliwilli.phantasien.web.resources.TestResource;
import de.tilliwilli.phantasien.web.resources.UserResource;

public class TestApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/test", TestResource.class);
		router.attach("/register", UserResource.class);

		return router;
	}
}
