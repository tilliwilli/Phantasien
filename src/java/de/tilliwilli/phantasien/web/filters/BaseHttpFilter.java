package de.tilliwilli.phantasien.web.filters;

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

/**
 * Base class for filters that only work with {@link HttpServletRequest HttpServletRequests} and
 * responses. Checks that the {@link ServletRequest} passed to {@link #doFilter} is actually an
 * <tt>HttpServletRequest</tt> (and for the response) and calls {@link #doFilterInternal} with
 * casted values.<br>
 * Also provides dummy implementations for {@link #init} and {@link #destroy}.
 * 
 */
public abstract class BaseHttpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		checkArgument(request instanceof HttpServletRequest);
		checkArgument(response instanceof HttpServletResponse);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		doFilterInternal(httpRequest, httpResponse, chain);
	}

	protected abstract void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

	@Override
	public void destroy() {
		// do nothing
	}

}
