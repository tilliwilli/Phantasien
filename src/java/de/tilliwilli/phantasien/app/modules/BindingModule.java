package de.tilliwilli.phantasien.app.modules;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.tilliwilli.phantasien.model.dao.UserDAO;
import de.tilliwilli.phantasien.model.dao.impl.OfyUserDAO;

public class BindingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserDAO.class).to(OfyUserDAO.class);
	}

	@Provides
	UserService provideUserService() {
		return UserServiceFactory.getUserService();
	}

}
