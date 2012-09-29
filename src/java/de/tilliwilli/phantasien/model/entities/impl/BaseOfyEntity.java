package de.tilliwilli.phantasien.model.entities.impl;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import de.tilliwilli.phantasien.model.entities.BaseEntity;

/**
 * Basic interface for all entities that should be persisted using {@link Objectify}.
 * 
 * @param <T>
 *           the type of the implementing class
 */
public interface BaseOfyEntity<T> extends BaseEntity {

	/**
	 * Returns the {@link Key} of this entity.
	 */
	public Key<T> getKey();

}
