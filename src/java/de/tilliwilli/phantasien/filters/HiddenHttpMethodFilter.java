package de.tilliwilli.phantasien.filters;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.google.inject.Singleton;

/**
 * A Filter that changes the request method of the request if an appropriate field is present in a
 * form. The name of the field to use can be given as an init parameter (use the static field
 * {@link #INIT_PARAMETER} ), and if not given defaults to the value of
 * {@link #DEFAULT_METHOD_PARAM}, which currently is <tt>_method</tt>.
 */
@Singleton
public class HiddenHttpMethodFilter extends HttpFilterBase {

	/**
	 * The name to use as init parameter for the field name when setting up this filter.
	 */
	public static final String INIT_PARAMETER = "paramname";

	/**
	 * The default method parameter: <code>_method</code>
	 */
	public static final String DEFAULT_METHOD_PARAM = "_method";

	/**
	 * The name of the parameter (field) that contains the request method to set.
	 */
	private String methodParam = DEFAULT_METHOD_PARAM;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String paramName = filterConfig.getInitParameter(INIT_PARAMETER);
		if (!Strings.isNullOrEmpty(paramName)) {
			this.methodParam = paramName;
		}
	}

	/**
	 * Do the acutal filtering. Wraps the request in a wrapper that returns the desired method when
	 * queried. Only wraps when the correct parameter is sent with a POST-request and when the value
	 * of that parameter is not empty.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		String method = request.getParameter(methodParam);
		if ("POST".equals(request.getMethod()) && !Strings.isNullOrEmpty(method)) {
			method = method.toUpperCase(Locale.ENGLISH);
			HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, method);
			filterChain.doFilter(wrapper, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
	 * {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}
}
