package de.tilliwilli.phantasien.model.providers;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.tilliwilli.phantasien.model.entities.User;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

public class CurrentUserProvider implements Provider<User> {

	@Inject
	private UserService userService;

	@Override
	public User get() {
		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();

		if (gaeUser == null) { return null; }
		String id = gaeUser.getUserId();

		User user = ofy().load().type(UserImpl.class).id(id).get();
		return user;
	}
}
