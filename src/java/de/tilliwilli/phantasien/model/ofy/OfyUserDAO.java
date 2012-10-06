package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import de.tilliwilli.phantasien.model.User;
import de.tilliwilli.phantasien.model.UserDAO;

/**
 * Objectify-compliant implementation of {@link UserDAO}.
 */
class OfyUserDAO implements UserDAO {

	@Override
	public Optional<User> byId(String id) {
		checkArgument(!Strings.isNullOrEmpty(id));
		return Optional.<User>fromNullable(ofy().load().type(OfyUser.class).id(id).get());
	}
}
