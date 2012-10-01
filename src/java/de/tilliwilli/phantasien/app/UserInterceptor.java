package de.tilliwilli.phantasien.app;

import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.AbstractInterceptorHandler;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import de.tilliwilli.phantasien.model.entities.User;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

public class UserInterceptor extends AbstractInterceptorHandler {

	public static String USER_SESSION_ATTRIBUTE = "__USER";
	public static String FREE_ACCESS_PATH = "/user/";

	private UserService userService;

	@Inject
	public UserInterceptor(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
		if (user != null) {
			return true;
		}

		if (!userService.isUserLoggedIn()) {
			try {
				StringBuffer url = request.getRequestURL();
				url.append(Strings.nullToEmpty(request.getQueryString()));
				String loginURL = userService.createLoginURL(url.toString());
				response.sendRedirect(loginURL);
				return false;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
		checkState(gaeUser != null);

		String id = gaeUser.getUserId();
		user = ofy().load().type(UserImpl.class).id(id).get();

		if (user == null) {
			// the user is not yet registered in our application, but has signed in into AppEngine
			String uri = request.getRequestURI();
			if (uri.startsWith(FREE_ACCESS_PATH)) {
				// if a tolerated path is requested, just go on with request processing
				return true;
			} else {
				// the requested url needs the user to have a profile in our application
				StringBuffer url = request.getRequestURL();
				url.append(Strings.nullToEmpty(request.getQueryString()));

				return false;
			}
		}

		// set the session user attribute and continue normal request processing
		session.setAttribute(USER_SESSION_ATTRIBUTE, user);
		return true;
	}
}
