package org.acme;

import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION) // garante que roda antes dos endpoints
public class MyApiKeyFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "app.api.key")
    String configureApiKey;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String apiKey = requestContext.getHeaderString("x-api-key");

        // ✅ Se a chave está ausente ou não bate com a configurada, bloqueia
        if (apiKey == null || !apiKey.equals(configureApiKey)) {
            throw new UnauthorizedException("API Key ausente ou inválida.");
        }

        // senão, continua normalmente
    }
}
