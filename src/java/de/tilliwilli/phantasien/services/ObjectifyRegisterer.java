package de.tilliwilli.phantasien.services;

import java.util.Set;

import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;

import de.tilliwilli.phantasien.model.entities.impl.BookImpl;
import de.tilliwilli.phantasien.model.entities.impl.CategoryImpl;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

/**
 * Helper class to register all entity classes at {@link ObjectifyService}.
 * {@link #registerEntities()} should be called on application startup.
 */
public class ObjectifyRegisterer {

	/**
	 * Static field holding all classes to register.
	 */
	private static Set<Class<?>> classes = Sets.newHashSet();

	static {
		classes.add(BookImpl.class);
		classes.add(CategoryImpl.class);
		classes.add(UserImpl.class);
	}

	/**
	 * Method that registers all entity types that should be managed by Objectify.
	 */
	public static void registerEntities() {
		for (Class<?> cls : classes) {
			ObjectifyService.register(cls);
		}
	}
}
