package de.tilliwilli.phantasien.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestResource {

	@GET
	public String doGet() {
		return "BLUBB!";
	}
}