package org.acme;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
public class MyApiKeyFilter  implements ContainerRequestFilter {

    @ConfigProperty(name = "app.api.key")
    String configureApiKey;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(containerRequestContext.getHeaderString("x-api-key").equals(configureApiKey)){
            throw new UnauthorizedException();
        }
    }
}
