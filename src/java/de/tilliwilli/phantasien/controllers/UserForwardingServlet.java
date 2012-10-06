package de.tilliwilli.phantasien.controllers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import de.tilliwilli.phantasien.app.Constants;
import de.tilliwilli.phantasien.filters.UserFilter;
import de.tilliwilli.phantasien.model.User;

/**
 * Simple servlet that forwards all requests specific to the user to his own profile page.
 * <p>
 * This means that requests like
 * <p>
 * <tt>/categories/123456/edit</tt>
 * <p>
 * get forwarded to
 * <p>
 * <tt>/users/&lt;user-id&gt;/categories/123456/edit</tt>.
 * <p>
 * Assumes that {@link UserFilter} is correctly set up for retrieval of the current user from the
 * session.
 */
public class UserForwardingServlet extends HttpServlet implements Constants {

	private static final long serialVersionUID = -5508519567687835856L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
		checkNotNull(user, "UserFilter must be applied before forwarding requests");

		String uri = request.getRequestURI();
		if (uri.length() > 0) {
			// skip the leading '/' if there is one
			uri = uri.substring(1);
		}
		String queryString = Strings.nullToEmpty(request.getQueryString());
		if (!queryString.isEmpty()) {
			queryString = '?' + queryString;
		}

		//@formatter:off
		String newURI = new StringBuilder()
			.append(USER_PATH)
			.append(user.getId())
			.append('/')
			.append(uri)
			.append(queryString)
			.toString();
		//@formatter:on

		// do the forward now
		request.getRequestDispatcher(newURI).forward(request, response);
	}
}
