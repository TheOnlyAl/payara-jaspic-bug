package de.adtelligence.jaspicbug;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequestScoped
@Path("auth")
public class AuthenticationResource {

	@Inject
	private SecurityContext securityContext;

	@Context
	private HttpServletRequest httpServletRequest;

	@Context
	private HttpServletResponse httpServletResponse;

	@GET
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public Response login(@NotNull @QueryParam("username") final String username,
			@NotNull @QueryParam("password") final String password) {
		final AuthenticationStatus authenticationStatus = securityContext.authenticate(httpServletRequest,
				httpServletResponse, AuthenticationParameters.withParams()
					.credential(new UsernamePasswordCredential(username, password)));
		return Response.ok("Status: " + authenticationStatus)
			.build();
	}

	@GET
	@Path("logout")
	public Response logout() {
		try {
			httpServletRequest.logout();
			final HttpSession session = httpServletRequest.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			return Response.ok()
				.build();
		} catch (final ServletException servletException) {
			servletException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.build();
		}
	}
}