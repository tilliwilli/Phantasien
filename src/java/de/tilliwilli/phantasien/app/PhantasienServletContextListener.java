package de.tilliwilli.phantasien.app;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

import de.tilliwilli.phantasien.app.modules.FilterModule;
import de.tilliwilli.phantasien.services.ObjectifyRegisterer;

/**
 * The {@link GuiceServletContextListener} that bootstraps all our {@link Module}s for dependency
 * injection and controller setup.
 */
public class PhantasienServletContextListener extends GuiceServletContextListener {

	/**
	 * Called when the servlet container starts our application. Does all the Guice-stuff and
	 * additionally registers all Objectify entities.
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		ObjectifyRegisterer.registerEntities();
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new FilterModule());
	}

}
