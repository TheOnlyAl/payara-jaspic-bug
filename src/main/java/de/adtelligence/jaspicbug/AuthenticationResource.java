package de.adtelligence.jaspicbug;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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