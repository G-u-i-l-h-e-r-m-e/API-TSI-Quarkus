package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

public class GeetingResourceV2 {

    @Produces(MediaType.TEXT_PLAIN)
    @RateLimit(value = 5, window = 10)
    @Fallback(
            fallbackMethod = "rateLimitFallback"
    )

    @GET
    @Path("/v2/hello")
    public Response hello() {
        return Response.ok("Hello from Quarkus REST").build();
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
