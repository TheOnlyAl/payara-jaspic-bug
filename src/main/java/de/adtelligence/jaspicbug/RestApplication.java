package de.adtelligence.jaspicbug;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/rest")
@DeclareRoles("secure")
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "", errorPage = ""))
public class RestApplication extends Application {

}