package de.adtelligence.jaspicbug;

import java.security.SecureRandom;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("load")
public class LoadResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response createLoad(@QueryParam("iterations") @DefaultValue("10000") final int iterations) {
		final SecureRandom secureRandom = new SecureRandom();

		double result = 0;
		for (int i = 0; i < iterations; i++) {
			result += secureRandom.nextDouble();
		}

		return Response.ok(result)
			.build();
	}
}