package de.adtelligence.jaspicbug;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/rest")
@DeclareRoles("secure")
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "", errorPage = ""))
public class RestApplication extends Application {

}