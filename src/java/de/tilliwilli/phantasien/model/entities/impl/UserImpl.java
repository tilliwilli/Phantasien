package de.tilliwilli.phantasien.model.entities.impl;

import lombok.Getter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.entities.User;

/**
 * Objectify-Implementation of a {@link User}.
 */
@Entity
@Cache
public class UserImpl implements User, BaseOfyEntity<UserImpl> {

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
	private UserImpl() {}

	/**
	 * Creates a new User based on a {@link com.google.appengine.api.users.User GAE user}.
	 */
	public UserImpl(com.google.appengine.api.users.User googleUser) {
		this.id = googleUser.getUserId();
	}

	@Override
	public Key<UserImpl> getKey() {
		return Key.create(UserImpl.class, id);
	}

}
