package de.tilliwilli.phantasien.model;


/**
 * Simple DAO to retreive {@link User} objects by different means.
 */
public interface UserDAO {

	/**
	 * Returns the {@link User} with the given <tt>id</tt>.
	 */
	public User getUserById(String id);

}
