package de.tilliwilli.phantasien.model.ofy;

import java.util.Set;

import com.google.common.collect.Sets;
import com.googlecode.objectify.ObjectifyService;

/**
 * Helper class to register all entity classes at {@link ObjectifyService}. You should call
 * {@link #registerEntities()} on application startup.
 */
class ObjectifyRegisterer {

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
	static void registerEntities() {
		for (Class<?> cls : classes) {
			ObjectifyService.register(cls);
		}
	}
}
