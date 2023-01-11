# Payara Jaspic Bug

This project can be used to reproduce a possible bug in Payara using Jaspic.

## Brief Summary

If you have a protected Web Resource and are Logged in using an authentication mechanism like `@CustomFormAuthenticationMechanismDefinition`, you can sometimes get a `HTTP 403 Foprbidden` status code if the server is under load.

This is probably some kind of concurrency issue.


## Expected Outcome

As long as you are logged in correctly, you should not get a `HTTP 403 Foprbidden`.

## Current Outcome

Sometimes calls will return with `HTTP 403 Foprbidden` while the server is under load.

## Reproducer

* Use this project to build the `jaspic-bug.war` Web Archive.
* Start a Payara Server using docker. For example: `docker run -p 8080:8080 -p4848:4848 payara/server-full:5.2022.5`.
* Deploy the Application
* Browse to `http://localhost:8080/jaspic-bug/`
* Click on `Login` to authenticate
* Click on `Check Security`. This will start a periodically check which will check if the protected resource can be accessed. This will write a `+` for every successfull call
* To generate load you can use [JMeter](https://jmeter.apache.org/download_jmeter.cgi) and use the provided test plan from `/src/test/resources/CreateLoad.jmx`. For example like this: `./jmeter -n -t src/test/resources/CreateLoad.jmx`
* When the Bug occurs you should get this output: `Ending Security check with result status: 403`

## Additional information

After further investigation it might be some problem with the `@AutoApplySession ` functionality which uses the following interceptor:
[AutoApplySessionInterceptor.java](https://github.com/payara/patched-src-security-soteria/blob/security-soteria-1.1-b01.payara-p4/impl/src/main/java/org/glassfish/soteria/cdi/AutoApplySessionInterceptor.java#L81)

The problem seems to be that the actual additional information which is returned is not correct for a call under load: 
[BaseContainerCallbackHandler.java](https://github.com/payara/Payara/blob/payara-server-5.2022.5/appserver/security/core-ee/src/main/java/com/sun/enterprise/security/jaspic/callback/BaseContainerCallbackHandler.java#L353)

The `SecurityContext.getCurrent().getAdditionalPrincipal()` will return null for the calls which result in a 403 unauthorized. And since the group information seems to be part of the additional information this is a problem.

There seems to be an really old bug with a similar problem.
[https://github.com/payara/Payara/issues/751](https://github.com/payara/Payara/issues/751)

## Operating System

Docker Image on Windows 10 using WSL 2

## JDK Version

Checked for JDK 8 (Payara 5.2022.5) and JDK 17 (Payara 6.2021.1)

# Payara Distribution

Payara Server Full Profile