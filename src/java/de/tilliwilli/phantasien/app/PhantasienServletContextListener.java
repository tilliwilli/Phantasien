package de.tilliwilli.phantasien.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

import de.tilliwilli.phantasien.app.modules.BindingModule;
import de.tilliwilli.phantasien.app.modules.ControllerModule;
import de.tilliwilli.phantasien.app.modules.FilterModule;

/**
 * The {@link GuiceServletContextListener} that bootstraps all our {@link Module}s for dependency
 * injection and controller setup.
 */
public class PhantasienServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		//@formatter:off
		return Guice.createInjector(
				new BindingModule(),
				new FilterModule(),
				new ControllerModule()
		);
		//@formatter:on
	}

}
