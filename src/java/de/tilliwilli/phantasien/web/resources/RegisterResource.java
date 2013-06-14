package de.tilliwilli.phantasien.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("register")
public class RegisterResource {

	@GET
	public String doGet() {
		return "TADA!";
	}
}
