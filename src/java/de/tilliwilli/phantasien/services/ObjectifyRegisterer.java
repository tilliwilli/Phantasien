package de.tilliwilli.phantasien.services;

import java.util.Set;

import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;

import de.tilliwilli.phantasien.model.entities.impl.OfyBook;
import de.tilliwilli.phantasien.model.entities.impl.OfyCategory;
import de.tilliwilli.phantasien.model.entities.impl.OfyUser;

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
		classes.add(OfyBook.class);
		classes.add(OfyCategory.class);
		classes.add(OfyUser.class);
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
