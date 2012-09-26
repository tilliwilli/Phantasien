package de.tilliwilli.phantasien.model;

import java.util.Set;

import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;

import de.tilliwilli.phantasien.model.entities.Association;
import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.Category;
import de.tilliwilli.phantasien.model.entities.User;

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
		classes.add(Association.class);
		classes.add(Book.class);
		classes.add(Category.class);
		classes.add(User.class);
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
