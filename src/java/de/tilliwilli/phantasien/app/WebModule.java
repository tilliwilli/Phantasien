package de.tilliwilli.phantasien.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.restlet.ext.servlet.ServerServlet;

import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

import de.tilliwilli.phantasien.web.TestApplication;
import de.tilliwilli.phantasien.web.filters.CharacterEncodingFilter;
import de.tilliwilli.phantasien.web.filters.GaeUserFilter;
import de.tilliwilli.phantasien.web.filters.HiddenHttpMethodFilter;
import de.tilliwilli.phantasien.web.filters.UserFilter;

/**
 * Simple {@link ServletModule} that sets up all {@link Filter}s needed for our application and
 * bindings directly related to them.
 */
public class WebModule extends ServletModule {

	@Override
	protected void configureServlets() {

		// CharacterEncodingFilter defaults to use UTF-8, which is what we want
		filter("/*").through(CharacterEncodingFilter.class);

		// automatically clean up the ObjectifyService after usage
		bind(ObjectifyFilter.class).in(Scopes.SINGLETON);
		filter("/*").through(ObjectifyFilter.class);

		// make sure to filter through GaeUserFilter _before_ UserFilter
		filter("/*").through(GaeUserFilter.class);
		filter("/*").through(UserFilter.class);

		// automatically change the request method when the associated field is present in a form
		filter("/*").through(HiddenHttpMethodFilter.class);

		// initialize and bind Restlet Servlet
		Map<String, String> initParams = new HashMap<>();
		initParams.put("org.restlet.application", TestApplication.class.getName());
		bind(ServerServlet.class).in(Singleton.class);
		serve("/*").with(ServerServlet.class, initParams);
	}
}
