package de.tilliwilli.phantasien.model;

import com.google.common.base.Optional;

/**
 * Simple DAO to retreive {@link User} objects by different means.
 */
public interface UserDAO {

	/**
	 * Returns the {@link User} with the given <tt>id</tt>.
	 */
	public Optional<User> byId(String id);

}
