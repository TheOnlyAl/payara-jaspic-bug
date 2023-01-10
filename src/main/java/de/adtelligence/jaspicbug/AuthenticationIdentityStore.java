package de.adtelligence.jaspicbug;

import java.util.HashSet;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
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