package de.tilliwilli.phantasien.filters;

import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.tilliwilli.phantasien.model.entities.User;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

@Singleton
public class UserFilter extends HttpFilterBase {

	public static String USER_SESSION_ATTRIBUTE = "__USER";

	private UserService userService;

	@Inject
	public UserFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
		if (user != null) {
			chain.doFilter(request, response);
			return;
		}

		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
		checkState(gaeUser != null,
				"There must always be a GAE user. Make sure to invoke GaeUserFilter before UserFilter!");

		String id = gaeUser.getUserId();
		user = ofy().load().type(UserImpl.class).id(id).get();

		if (user == null) {
			// the user is logged into GAE, but has no profile in our application yet
			StringBuffer url = request.getRequestURL();
			url.append(Strings.nullToEmpty(request.getQueryString()));
		}

		// set the session user attribute and continue normal request processing
		session.setAttribute(USER_SESSION_ATTRIBUTE, user);
		chain.doFilter(request, response);
	}

}
