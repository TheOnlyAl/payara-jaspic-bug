package de.adtelligence.jaspicbug;

import java.security.SecureRandom;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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