package de.tilliwilli.phantasien.app.modules;

import org.zdevra.guice.mvc.ViewResolver;

import com.google.inject.AbstractModule;

import de.tilliwilli.phantasien.app.WebInfJSPResolver;

public class LimeFixingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ViewResolver.class).to(WebInfJSPResolver.class);
	}

}
