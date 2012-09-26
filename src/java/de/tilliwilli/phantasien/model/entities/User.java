package de.tilliwilli.phantasien.model.entities;

import lombok.Getter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.entities.base.ActiveRecord;

/**
 * @author Tilman
 */
@Entity
@Cache
public class User extends ActiveRecord<User> {

	/**
	 * The ID in the datastore. For Users, this is set to the ID of the
	 * {@link com.google.appengine.api.users.User GoogleUser}, which is said to be unique and stable
	 * even if users change their e-mail or other stuff.
	 */
	@Id
	@Getter
	private String id;

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private User() {}

	/**
	 * Creates a new User based on a {@link com.google.appengine.api.users.User GAE user}.
	 */
	public User(com.google.appengine.api.users.User googleUser) {
		this.id = googleUser.getUserId();
	}

	@Override
	public Key<User> getKey() {
		return Key.create(User.class, id);
	}

}
