package de.tilliwilli.phantasien.app;

import java.util.Map;

import javax.servlet.Filter;

import com.google.common.collect.Maps;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

/**
 * Simple {@link ServletModule} that sets up all {@link Filter}s needed for our application.
 */
public class FilterModule extends ServletModule {

	@Override
	protected void configureServlets() {
		// set CharacterEncodingFilter to make all requests have a UTF-8 encoding
		Map<String, String> params = Maps.newHashMap();
		params.put("encoding", "UTF-8");
		filter("/*").through(CharacterEncodingFilter.class, params);

		// automatically clean up the ObjectifyService
		filter("/*").through(ObjectifyFilter.class);
	}
}
