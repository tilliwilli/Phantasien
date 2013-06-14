package de.tilliwilli.phantasien.app;

import javax.servlet.Filter;

import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

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
		filterRegex("/[^_]*").through(CharacterEncodingFilter.class);

		// automatically clean up the ObjectifyService after usage
		bind(ObjectifyFilter.class).in(Scopes.SINGLETON);
		filterRegex("/[^_]*").through(ObjectifyFilter.class);

		// make sure to filter through GaeUserFilter _before_ UserFilter
		filterRegex("/[^_]*").through(GaeUserFilter.class);
		filterRegex("/[^_]*").through(UserFilter.class);

		// automatically change the request method when the associated field is present in a form
		filterRegex("/[^_]*").through(HiddenHttpMethodFilter.class);

		bind(FilterDispatcher.class).in(Scopes.SINGLETON);
		filter("/*").through(FilterDispatcher.class);
	}
}
