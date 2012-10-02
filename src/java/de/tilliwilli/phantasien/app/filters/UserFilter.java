package de.tilliwilli.phantasien.app.filters;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.tilliwilli.phantasien.model.entities.User;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

@Singleton
public class UserFilter implements Filter {

	public static String USER_SESSION_ATTRIBUTE = "__USER";

	@Inject
	UserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		checkArgument(request instanceof HttpServletRequest);
		HttpServletRequest httpReq = (HttpServletRequest) request;

		HttpSession session = httpReq.getSession();
		User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
		if (user != null) {
			chain.doFilter(request, response);
			return;
		}

		com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
		checkState(gaeUser != null);

		String id = gaeUser.getUserId();
		user = ofy().load().type(UserImpl.class).id(id).get();

		if (user == null) {
			// the user is logged in into GAE, but has no profile in our application yet
			StringBuffer url = httpReq.getRequestURL();
			url.append(Strings.nullToEmpty(httpReq.getQueryString()));
		}

		// set the session user attribute and continue normal request processing
		session.setAttribute(USER_SESSION_ATTRIBUTE, user);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}

}
