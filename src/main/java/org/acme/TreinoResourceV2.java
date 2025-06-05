package org.acme;


import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.net.URI;
import java.util.List;

@Path("api/v2/treinos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TreinoResourceV2 {

    @GET
    @RateLimit(value = 3, window = 10)
    @Fallback(fallbackMethod = "rateLimitFallback")
    public Response listarTodos() {
        return Response.ok(Treino.listAll()).build();
    }

    public Response rateLimitFallback() {
        return Response.status(429)
                .entity("⚠️ Limite de requisições atingido. Tente novamente mais tarde.")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Treino treino = Treino.findById(id);
        if (treino == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Treino com ID " + id + " não encontrado.")
                    .build();
        }
        return Response.ok(treino).build();
    }

    @POST
    @Transactional
    public Response criar(
            Treino treino,
            @HeaderParam("Idempotency-Key") String idempotencyKey,
            @HeaderParam("X-API-Key") String chave) {

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Response.status(400)
                    .entity("Cabeçalho 'Idempotency-Key' é obrigatório.")
                    .build();
        }

        boolean chaveUsada = IdempotencyKey.find("chave = ?1", idempotencyKey)
                .firstResultOptional().isPresent();
        if (chaveUsada) {
            return Response.status(409)
                    .entity("Requisição duplicada. Essa chave já foi usada.")
                    .build();
        }

        treino.persist(); // erros de validação tratados globalmente

        IdempotencyKey registro = new IdempotencyKey();
        registro.chave = idempotencyKey;
        registro.metodo = "POST";
        registro.endpoint = "api/v2/treinos";
        registro.persist();

        URI location = URI.create("/treinos/" + treino.id);
        return Response.created(location).entity(treino).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Treino treino) {
        Treino entidade = Treino.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Treino com ID " + id + " não encontrado para atualização.")
                    .build();
        }

        entidade.nome = treino.nome;
        entidade.data = treino.data;
        entidade.duracao = treino.duracao;
        entidade.objetivo = treino.objetivo;
        entidade.exercicios = treino.exercicios;
        entidade.notas = treino.notas;

        return Response.ok(entidade).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Treino.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Treino com ID " + id + " não encontrado para exclusão.")
                .build();
    }

    @GET
    @Path("/busca/treino/{nome}")
    public List<Treino> buscarPorNome(@PathParam("nome") String nome) {
        return Treino.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }
}

