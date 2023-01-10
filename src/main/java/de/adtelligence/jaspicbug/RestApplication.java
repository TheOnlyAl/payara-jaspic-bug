/*
 * @(#)RestApplication.java
 *
 * Copyright 2023 ADTELLIGENCE GmbH. All rights reserved.
 */
package de.adtelligence.jaspicbug;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@DeclareRoles("secure")
public class RestApplication extends Application {

}