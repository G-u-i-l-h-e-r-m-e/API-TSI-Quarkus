package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/api-keys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class ApiKeyResource {

    /** Gera uma nova chave */
    @POST
    public Response gerarNovaChave(@QueryParam("role") String role) {
        ApiKey nova = new ApiKey();
        nova.valor = UUID.randomUUID().toString(); // gera chave única
        nova.active = true;
        nova.role = role != null ? role : "DEFAULT";
        nova.persist();

        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    /** Lista todas as chaves */
    @GET
    public List<ApiKey> listarTodas() {
        return ApiKey.listAll();
    }

    /** Ativa ou desativa uma chave */
    @PATCH
    @Path("/{id}")
    public Response atualizarStatus(@PathParam("id") Long id, @QueryParam("ativo") boolean ativo) {
        ApiKey chave = ApiKey.findById(id);
        if (chave == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Chave não encontrada").build();
        }
        chave.active = ativo;
        return Response.ok(chave).build();
    }

    /** (Opcional) Exclui uma chave */
    @DELETE
    @Path("/{id}")
    public Response removerChave(@PathParam("id") Long id) {
        boolean removida = ApiKey.deleteById(id);
        if (!removida) {
            return Response.status(Response.Status.NOT_FOUND).entity("Chave não encontrada").build();
        }
        return Response.noContent().build();
    }
}