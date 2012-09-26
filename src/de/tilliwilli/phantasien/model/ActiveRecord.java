package de.tilliwilli.phantasien.model;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;

/**
 * @author Tilman
 */
public abstract class ActiveRecord<T> {

	/**
	 * Returns the key of this entity.
	 */
	public abstract Key<T> getKey();

	/**
	 * Remove this entity from the datastore.
	 */
	public void delete() {
		ofy().delete().entity(this).now();
	}

	/**
	 * Put this entity into the datastore.
	 */
	@SuppressWarnings("unchecked")
	public Key<T> save() {
		return (Key<T>) ofy().save().entity(this).now();
	}
}
