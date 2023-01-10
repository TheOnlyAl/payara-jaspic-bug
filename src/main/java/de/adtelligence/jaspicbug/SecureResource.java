/*
 * @(#)SecureResource.java
 *
 * Copyright 2023 ADTELLIGENCE GmbH. All rights reserved.
 */
package de.adtelligence.jaspicbug;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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