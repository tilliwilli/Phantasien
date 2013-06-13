package de.tilliwilli.phantasien.web.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class TestResource extends ServerResource {

	@Get
	public String doTest() {
		return "WORKS!";
	}

}
