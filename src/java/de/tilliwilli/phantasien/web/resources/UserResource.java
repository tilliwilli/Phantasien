package de.tilliwilli.phantasien.web.resources;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UserResource extends ServerResource {

	@Get
	public String doGet() {
		return "BLUBB!";
	}

}
