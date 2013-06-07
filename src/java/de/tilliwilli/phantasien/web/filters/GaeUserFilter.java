package de.tilliwilli.phantasien.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * <p>
 * Filter that makes sure that the user is logged into Google AppEngine.
 * </p>
 * <p>
 * If the user is not logged in (that is, if {@link UserService#isUserLoggedIn()} returns
 * <tt>false</tt>), redirects the user to the GAE login-page, and after logging in, redirects the
 * user to the same page he requested currently.
 * </p>
 */
@Singleton
public class GaeUserFilter extends BaseHttpFilter {

	/**
	 * The GAE {@link UserService}.
	 */
	private UserService userService;

	@Inject
	public GaeUserFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!userService.isUserLoggedIn() && !request.getRequestURI().startsWith("/_ah")) {
			StringBuffer url = request.getRequestURL();
			String queryString = Strings.nullToEmpty(request.getQueryString());
			if (!queryString.isEmpty()) {
				url.append('?');
				url.append(queryString);
			}
			String loginURL = userService.createLoginURL(url.toString());
			response.sendRedirect(loginURL);
		} else {
			chain.doFilter(request, response);
		}
	}

}
