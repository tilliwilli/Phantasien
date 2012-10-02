package de.tilliwilli.phantasien.app.filters;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GaeUserFilter implements Filter {

	@Inject
	UserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		checkArgument(request instanceof HttpServletRequest);
		checkArgument(response instanceof HttpServletResponse);

		if (!userService.isUserLoggedIn()) {
			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpServletResponse httpRes = (HttpServletResponse) response;
			StringBuffer url = httpReq.getRequestURL();
			url.append(Strings.nullToEmpty(httpReq.getQueryString()));
			String loginURL = userService.createLoginURL(url.toString());
			httpRes.sendRedirect(loginURL);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {}

}
