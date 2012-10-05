package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Strings;

import de.tilliwilli.phantasien.model.User;
import de.tilliwilli.phantasien.model.UserDAO;

/**
 * Objectify-compliant implementation of a {@link UserDAO}.
 */
class OfyUserDAO implements UserDAO {

	@Override
	public User getUserById(String id) {
		checkArgument(!Strings.isNullOrEmpty(id));
		return ofy().load().type(OfyUser.class).id(id).get();
	}
}
