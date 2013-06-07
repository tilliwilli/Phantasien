package de.tilliwilli.phantasien.app;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.ViewResolver;
import org.zdevra.guice.mvc.exceptions.NoViewException;
import org.zdevra.guice.mvc.views.JspView;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class WebInfJSPResolver implements ViewResolver {

	@Inject
	private Injector injector;

	@Override
	public void resolve(ViewPoint view, ModelMap model, HttpServlet servlet, HttpServletRequest req,
			HttpServletResponse resp) {
		if (view == null || view == ViewPoint.NULL_VIEW) {
			throw new NoViewException(req);
		}

		if (view instanceof NamedView) {
			NamedView namedView = (NamedView) view;
			try {
				view = injector.getInstance(Key.get(ViewPoint.class, Names.named(namedView.getName())));
			} catch (ConfigurationException e) {
				view = new JspView("/WEB-INF/jsp/" + namedView.getName() + ".jsp");
			}
		}

		view.render(model, servlet, req, resp);
	}
}
