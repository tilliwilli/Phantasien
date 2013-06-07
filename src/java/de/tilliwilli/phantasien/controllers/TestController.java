package de.tilliwilli.phantasien.controllers;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.UriParameter;
import org.zdevra.guice.mvc.annotations.View;

@Controller
public class TestController {

	@Path("/hello/(.*)")
	@View("hello")
	public ModelMap doAction(@UriParameter(1) String name) {
		ModelMap m = new ModelMap();
		m.put("name", name);
		return m;
	}
}
