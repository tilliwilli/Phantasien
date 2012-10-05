package de.tilliwilli.phantasien.model.ofy;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.googlecode.objectify.annotation.Entity;

import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.UserDAO;

/**
 * Guice-{@link Module} that binds all DAOs to their Objectify-compliant implementations and
 * registers all Objectify-{@link Entity Entities} at the objectify factory.
 */
public class ObjectifyModule extends AbstractModule {

	@Override
	protected void configure() {
		ObjectifyRegisterer.registerEntities();
		bind(UserDAO.class).to(OfyUserDAO.class);
		bind(BookDAO.class).to(OfyBookDAO.class);
	}
}
