package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.security.Key;

@Path("/")
public class GreetingResource {


    @Produces(MediaType.TEXT_PLAIN)
    @RateLimit(value = 5, window = 10)
    @Fallback(
        fallbackMethod = "rateLimitFallback"
    )

    @GET
    @Path("/v1/hello")
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

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListEntities(){
        return Response.ok().entity(MyEntityRepository.listAll()).build();
    }


    public Response rateLimitFallback(){
        return Response.status(429).entity("You're doing too mani requests, our limits are").build();
    }
}
