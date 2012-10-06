package de.tilliwilli.phantasien.app.modules;

import javax.servlet.Filter;

import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

import de.tilliwilli.phantasien.filters.CharacterEncodingFilter;
import de.tilliwilli.phantasien.filters.GaeUserFilter;
import de.tilliwilli.phantasien.filters.HiddenHttpMethodFilter;
import de.tilliwilli.phantasien.filters.UserFilter;

/**
 * Simple {@link ServletModule} that sets up all {@link Filter}s needed for our application and
 * bindings directly related to them.
 */
public class FilterModule extends ServletModule {

	@Override
	protected void configureServlets() {
		// CharacterEncodingFilter defaults to use UTF-8, which is what we want
		filter("/*").through(CharacterEncodingFilter.class);

		// automatically clean up the ObjectifyService after usage
		filter("/*").through(ObjectifyFilter.class);

		// make sure to filter through GaeUserFilter _before_ UserFilter
		filter("/*").through(GaeUserFilter.class);
		filter("/*").through(UserFilter.class);

		// automatically change the request method when the associated field is present in a form
		filter("/*").through(HiddenHttpMethodFilter.class);
	}
}
