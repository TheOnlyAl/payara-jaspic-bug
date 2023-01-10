package de.adtelligence.jaspicbug;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("secure")
@RolesAllowed("secure")
public class SecureResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSecrets() {
		return Response.ok("Secret Text")
			.build();
	}
}