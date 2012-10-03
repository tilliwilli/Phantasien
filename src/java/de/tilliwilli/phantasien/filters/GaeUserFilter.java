package de.tilliwilli.phantasien.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GaeUserFilter extends HttpFilterBase {

	private UserService userService;

	@Inject
	public GaeUserFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!userService.isUserLoggedIn()) {
			StringBuffer url = request.getRequestURL();
			url.append(Strings.nullToEmpty(request.getQueryString()));
			String loginURL = userService.createLoginURL(url.toString());
			response.sendRedirect(loginURL);
		} else {
			chain.doFilter(request, response);
		}
	}

}
