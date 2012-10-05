package de.tilliwilli.phantasien.model.ofy;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import de.tilliwilli.phantasien.model.BaseEntity;

/**
 * Basic interface for all entities that should be persisted using {@link Objectify}.
 * 
 * @param <T>
 *           the type of the implementing class
 */
interface OfyBaseEntity<T> extends BaseEntity {

	/**
	 * Returns the {@link Key} of this entity.
	 */
	public Key<T> getKey();

}
