package de.tilliwilli.phantasien.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.inject.Singleton;

/**
 * Simple {@link Filter} that makes sure that a character encoding is set for each request. If no
 * {@value #INIT_PARAMETER} init parameter is set, defaults to use {@link Charsets#UTF_8 UTF-8} .
 */
@Singleton
public class CharacterEncodingFilter implements Filter {

	/**
	 * The encoding to set. Defaults to <tt>UTF-8</tt> if not set as init parameter.
	 */
	private String encoding = "UTF-8";

	/**
	 * The name of the parameter to use as init parameter for this filter.
	 */
	public static final String INIT_PARAMETER = "encoding";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String enc = filterConfig.getInitParameter("encoding");
		if (!Strings.isNullOrEmpty(enc)) {
			this.encoding = enc;
		}
	}

	/**
	 * Do the actual filtering. Since the <tt>init</tt>-method already makes sure that
	 * {@link #encoding} always contains a sane value, no checking is done here at all and the
	 * encoding is simply set.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
