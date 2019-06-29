package com.microservices.strava.api;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "Strava Application",
                version = "1.0.0",
                description = "Strava API",
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(
                        email = "raphael.alex@gmail.com",
                        name = "Raphael",
                        url = "https://github.com/aelkz"),
                termsOfService = ""),
        externalDocs = @ExternalDocumentation(
                description = "Additional information",
                url = "https://bar.foo/info"),
        servers = @Server(
                description = "Foo description",
                url = "http://localhost:8080/{env}",
                variables = @ServerVariable(
                        name = "env",
                        description = "Server variable",
                        enumeration = {"dev", "prod"},
                        defaultValue = "dev")
        ),
        tags = @Tag(
                name = "Type",
                description = "API type")
)
@ApplicationPath("/api/v1")
public class StravaApplication extends Application { }
