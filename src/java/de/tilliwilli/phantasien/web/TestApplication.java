package de.tilliwilli.phantasien.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import de.tilliwilli.phantasien.web.resources.TestResource;

public class TestApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attach("/", TestResource.class);

		//		GaeAuthenticator gaeFilter = new GaeAuthenticator(getContext());
		//		gaeFilter.setNext(router);

		return router;
	}
}
