/*
 * @(#)IdentityStore.java
 *
 * Copyright 2023 ADTELLIGENCE GmbH. All rights reserved.
 */
package de.adtelligence.jaspicbug;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@ManagedBean
@RequestScoped
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "", errorPage = ""))
public class AuthenticationIdentityStore implements IdentityStore {

	private boolean isValidCredential(final UsernamePasswordCredential credential) {
		return "user".equals(credential.getCaller()) && "password".equals(credential.getPasswordAsString());
	}

	public CredentialValidationResult validate(final UsernamePasswordCredential credential) {
		if (isValidCredential(credential)) {
			final Set<String> groups = new HashSet<>();
			groups.add("secure");

			return new CredentialValidationResult(credential.getCaller(), groups);
		} else {
			return CredentialValidationResult.INVALID_RESULT;
		}
	}
}