package de.tilliwilli.phantasien.filters;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.tilliwilli.phantasien.model.dao.UserDAO;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * <p>
 * Filter that makes sure that the user has a user profile in our application, and stores that
 * profile in the session.
 * </p>
 * <p>
 * <b>Requires the user to be logged into GAE!</b> Make sure to filter the request through
 * {@link GaeUserFilter} before applying this filter!<br>
 * </p>
 * <p>
 * If there is a profile in the data store, the filter retrieves it, puts it into the sesseion and
 * calls the filter chain. The name of the request attribute is the value of
 * {@link #USER_SESSION_ATTRIBUTE} (currently " <tt>__USER</tt>").
 * </p>
 * <p>
 * If there is no profile yet, the filter forwards the user to a registering controller. After
 * registering, the user is redirected to the url that was requested currently.
 * </p>
 */
@Singleton
public class UserFilter extends HttpFilterBase {

	/**
	 * The name of the session attribute that will hold the user object.
	 */
	public static final String USER_SESSION_ATTRIBUTE = "__USER";

	/**
	 * The GAE {@link UserService} for retrieval of the GAE user.
	 */
	private UserService userService;

	/**
	 * Our {@link UserDAO} to retrieve the profile of the user.
	 */
	private UserDAO userDao;

	@Inject
	public UserFilter(UserService userService, UserDAO userDao) {
		this.userService = userService;
		this.userDao = userDao;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = request.getSession();
		if (session.getAttribute(USER_SESSION_ATTRIBUTE) != null) {
			chain.doFilter(request, response);
			return;
		}

		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
		checkState(gaeUser != null,
				"There must always be a GAE user. Make sure to invoke GaeUserFilter before UserFilter.");

		String id = gaeUser.getUserId();
		User user = userDao.getUserById(id);

		if (user == null) {
			// the user is logged into GAE, but has no profile in our application yet
			// --> forward to register controller (no redirect!)
			request.getRequestDispatcher("/register").forward(request, response);
		} else {
			// set the session user attribute and continue normal request processing
			session.setAttribute(USER_SESSION_ATTRIBUTE, user);
			chain.doFilter(request, response);
		}
	}
}
