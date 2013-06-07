package de.tilliwilli.phantasien.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.util.Modules;

import de.tilliwilli.phantasien.app.modules.BindingModule;
import de.tilliwilli.phantasien.app.modules.ControllerModule;
import de.tilliwilli.phantasien.app.modules.FilterModule;
import de.tilliwilli.phantasien.app.modules.LimeFixingModule;
import de.tilliwilli.phantasien.providers.ProvidersModule;

/**
 * The {@link GuiceServletContextListener} that bootstraps all our {@link Module}s for dependency
 * injection and controller setup.
 */
public class PhantasienServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		//@formatter:off
		Module allModules = Modules.combine(
				new BindingModule(),
				new FilterModule(),
				new ControllerModule(),
				new ProvidersModule()
		);
		
		return Guice.createInjector(
			Modules.override(allModules).with(new LimeFixingModule())
		);
		//@formatter:on
	}

}
