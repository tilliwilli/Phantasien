package de.tilliwilli.phantasien.app.modules;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.tilliwilli.phantasien.model.ofy.ObjectifyModule;

/**
 * An {@link AbstractModule} that sets up all bindings that are independent from Serlvet or LimeMVC
 * contexts.
 */
public class BindingModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ObjectifyModule());
	}

	@Provides
	UserService provideUserService() {
		return UserServiceFactory.getUserService();
	}

}
