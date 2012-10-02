package de.tilliwilli.phantasien.app.filters;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.base.Strings;
import com.google.inject.Singleton;

@Singleton
public class HiddenHttpMethodFilter implements Filter {

	/** Default method parameter: <code>_method</code> */
	public static final String DEFAULT_METHOD_PARAM = "_method";

	private String methodParam = DEFAULT_METHOD_PARAM;

	public HiddenHttpMethodFilter() {}

	public HiddenHttpMethodFilter(String methodParam) {
		checkArgument(!Strings.isNullOrEmpty(methodParam));
		this.methodParam = methodParam;
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

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		checkArgument(request instanceof HttpServletRequest,
				"HiddenHttpMethodFilter only works for HTTP requests!");
		HttpServletRequest httpReq = (HttpServletRequest) request;

		String method = httpReq.getParameter(methodParam);
		if ("POST".equals(httpReq.getMethod()) && !Strings.isNullOrEmpty(method)) {
			method = method.toUpperCase(Locale.ENGLISH);
			HttpServletRequest wrapper = new HttpMethodRequestWrapper(httpReq, method);
			filterChain.doFilter(wrapper, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
