package de.tilliwilli.phantasien.model.dao.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Strings;

import de.tilliwilli.phantasien.model.dao.UserDAO;
import de.tilliwilli.phantasien.model.entities.User;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

public class UserDAOImpl implements UserDAO {

	@Override
	public User getUserById(String id) {
		checkArgument(!Strings.isNullOrEmpty(id));
		return ofy().load().type(UserImpl.class).id(id).get();
	}
}
