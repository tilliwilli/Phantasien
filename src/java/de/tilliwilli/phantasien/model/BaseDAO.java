/**
 * 
 */
package de.tilliwilli.phantasien.model;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

/**
 * @author Tilman
 * 
 */
public abstract class BaseDAO<T> {

	protected abstract Class<T> getEntityClass();

	public T getById(Long id) {
		return ofy().load().type(getEntityClass()).id(id).get();
	}

	public List<T> getAll() {
		return ofy().load().type(getEntityClass()).list();
	}

	public List<T> getAllByParent(Object parent) {
		return ofy().load().type(getEntityClass()).ancestor(parent).list();
	}

}
