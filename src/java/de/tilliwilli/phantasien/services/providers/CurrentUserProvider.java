package de.tilliwilli.phantasien.services.providers;

import static com.google.common.base.Preconditions.checkState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

import de.tilliwilli.phantasien.filters.UserFilter;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * Request-scoped provider for the {@link User} object of the current user. Depends on the current
 * request to get the user from the session. Therefore requires {@link UserFilter} to be applied to
 * the request beforehand, and throws an unchecked exception if that is not the case.
 * <p>
 * Eventually, this means that User objects can only be injected into controllers that are on the
 * filter path for {@link UserFilter}.
 */
@RequestScoped
public class CurrentUserProvider implements Provider<User> {

	private final HttpSession session;

	@Inject
	public CurrentUserProvider(HttpServletRequest request) {
		this.session = request.getSession();
	}

	@Override
	public User get() {
		User user = (User) session.getAttribute(UserFilter.USER_SESSION_ATTRIBUTE);
		checkState(user != null, "Cannot inject User when UserFilter is not used for the request.");
		return user;
	}
}
