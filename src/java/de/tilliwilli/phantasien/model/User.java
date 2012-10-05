package de.tilliwilli.phantasien.model;

import java.util.Map;

import com.google.common.base.Optional;

/**
 * A User is a single end-user of the application. Users manage {@link Book books}. They can define
 * {@link Category categories} and assign them to books, and can set {@link ReadState read states}
 * of the books they manage.<br>
 * They may also have settings, e.g. what data to show in the frontend. Settings are simple
 * String-to-Object mappings.
 */
public interface User extends BaseEntity {

	/**
	 * Returns the value of the setting with name <tt>name</tt>.
	 * 
	 * @param name
	 *           the name of the setting to retrieve
	 * @return an {@link Optional} containing the value of the setting
	 */
	public Optional<Object> get(String name);

	/**
	 * Sets a setting with the given <tt>name</tt> to the given <tt>value</tt>. If a setting with
	 * this name already exists, it is overwritten.
	 * 
	 * @param name
	 *           the name of the setting
	 * @param value
	 *           the value of the setting
	 * @return <tt>this</tt> for convenience
	 */
	public User set(String name, Object value);

	/**
	 * Returns a {@link Map} containing all settings this user has defined. The map is
	 * <b>immutable</b>, and it might be empty.
	 */
	public Map<String, Object> getAllSettings();

	/**
	 * Returns <tt>true</tt> if the profile of this user should be publicly accessible.
	 */
	public boolean isPublicProfile();

	/**
	 * Sets the publicity flag of this user's profile.
	 * 
	 * @param current
	 *           <tt>true</tt> if the profile of this user should be publicly accessible,
	 *           <tt>false</tt> otherwise
	 * @return <tt>this</tt> for convenience.
	 */
	public User setPublicProfile(boolean publicProfile);

}
