package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;


@Path("/api/v2")
public class GeetingResourceV2 {

    @GET
    @Path("/hello")
    @RateLimit(value = 3, window = 10)
    @Fallback(fallbackMethod = "rateLimitFallback")
    public String hello() {
        return "Olá da API versão 2!";
    }

    // Método fallback com MESMA assinatura (exceto que não usa anotações)
    public String rateLimitFallback() {
        return "⚠️ Limite de requisições atingido. Tente novamente mais tarde.";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEntity(@HeaderParam("x-idempotency-key")String key,MyEntityRepository entity){
        if (key == null)
            return Response.status(400).entity("Sem chave de idempotencia").build();

        var keyRecord = IdempotencyRecord.find("keyRecord = ?1", key).firstResultOptional();
        if(keyRecord.isEmpty()){
            var newKey = new IdempotencyRecord();
            newKey.keyRecord = key;
            newKey.persist();
            entity.persist();

        }
        entity.persist();
        return Response.status(201).build();
    }

}
