package de.tilliwilli.phantasien.app.modules;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class BindingModule extends AbstractModule {

	@Override
	protected void configure() {}

	@Provides
	UserService provideUserService() {
		return UserServiceFactory.getUserService();
	}

}
