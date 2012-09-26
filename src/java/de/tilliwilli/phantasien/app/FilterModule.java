package de.tilliwilli.phantasien.app;

import java.util.Map;

import org.zdevra.guice.mvc.MvcModule;

import com.google.common.collect.Maps;
import com.googlecode.objectify.ObjectifyFilter;

public class FilterModule extends MvcModule {

	@Override
	protected void configureControllers() {
		// map used for filter parameters
		Map<String, String> params = Maps.newHashMap();

		// set CharacterEncodingFilter to make all requests have a UTF-8 encoding
		params.put("encoding", "UTF-8");
		filter("/*").through(CharacterEncodingFilter.class, params);

		// automatically clean up the ObjectifyService
		filter("/*").through(ObjectifyFilter.class);
	}
}
