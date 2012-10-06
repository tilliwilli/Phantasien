package de.tilliwilli.phantasien.filters;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.tilliwilli.phantasien.model.User;
import de.tilliwilli.phantasien.model.UserDAO;

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
 * {@link #SESSION_ATTRIBUTE} (currently " <tt>__USER</tt>").
 * </p>
 * <p>
 * If there is no profile yet, the filter forwards the user to a registering controller. After
 * registering, the user is redirected to the url that was requested currently.
 * </p>
 */
@Singleton
public class UserFilter extends BaseHttpFilter {

	/**
	 * The name of the session attribute that will hold the user object of the user currently logged
	 * in.
	 */
	public static final String SESSION_ATTRIBUTE = "__USER";

	/**
	 * The path-fragment that user paths start with.
	 */
	public static final String USER_PATH = "/user/";

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
		User sessionUser = (User) session.getAttribute(SESSION_ATTRIBUTE);

		if (sessionUser != null) {
			// user has a profile in the datastore that is already in the session
			chain.doFilter(request, response);
			return;
		}

		Optional<User> user = getUserFromDAO();
		if (user.isPresent()) {
			// user has a profile in the datastore, so put it into session and go on
			session.setAttribute(SESSION_ATTRIBUTE, user.get());
			chain.doFilter(request, response);
			return;
		}

		// user has no profile now

		Optional<String> pathIdentifier = getIdentifierFromPath(request.getRequestURI());
		if (!pathIdentifier.isPresent()) {
			// user has to be registered to access anything outside of "/user"
			response.sendRedirect("/register");
			return;
		}

		Optional<User> requestedUser = userDao.byId(pathIdentifier.get());
		if (!requestedUser.isPresent()) {
			// invalid user profile was requested
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else if (!requestedUser.get().isPublicProfile()) {
			// requested profile is private
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// user has requested a publicly accessible profile without being logged in himself
		chain.doFilter(request, response);
	}

	private Optional<String> getIdentifierFromPath(String uri) {
		if (uri.startsWith(USER_PATH)) {
			int start = USER_PATH.length();
			int end = uri.indexOf('/', start);
			if (end == -1) {
				// when there is no trailing '/' use the whole tail
				end = uri.length();
			}
			if (start < end && end <= uri.length()) {
				return Optional.of(uri.substring(start, end));
			}
		}
		return Optional.absent();
	}

	private Optional<User> getUserFromDAO() {
		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
		checkState(gaeUser != null,
				"There must always be a GAE user. Make sure to invoke GaeUserFilter before UserFilter.");

		String id = gaeUser.getUserId();
		return userDao.byId(id);
	}
}
