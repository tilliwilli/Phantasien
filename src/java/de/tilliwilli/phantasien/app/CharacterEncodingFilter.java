package de.tilliwilli.phantasien.app;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.common.base.Strings;

/**
 * Simple {@link Filter} that makes sure that a character encoding is set for each request.
 */
public class CharacterEncodingFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		checkState(!Strings.isNullOrEmpty(encoding), "%s needs an \"encoding\" init parameter!", this
				.getClass().getName());
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
