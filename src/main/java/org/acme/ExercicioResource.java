package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.net.URI;
import java.util.List;

@Path("/exercicios")
@Produces(MediaType.APPLICATION_JSON)

@Consumes(MediaType.APPLICATION_JSON)
public class ExercicioResource {

    @GET
    @RateLimit(value = 3, window = 10) // 3 requisições a cada 10 segundos
    @Fallback(fallbackMethod = "rateLimitFallback")
    public Response listarTodos() {
        return Response.ok(Exercicio.listAll()).build();
    }

    // fallback chamado automaticamente quando estourar o limite
    public Response rateLimitFallback() {
        return Response.status(429)
                .entity("⚠️ Limite de requisições atingido. Tente novamente mais tarde.")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Exercicio exercicio = Exercicio.findById(id);
        if (exercicio == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Exercício com ID " + id + " não encontrado.")
                    .build();
        }
        return Response.ok(exercicio).build();
    }


    @POST
    @Transactional
    public Response criar(
            Exercicio exercicio,
            @HeaderParam("Idempotency-Key") String idempotencyKey,
            @HeaderParam("X-API-Key") String chave) {

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Response.status(400).entity("Cabeçalho 'Idempotency-Key' é obrigatório.").build();
        }

        boolean chaveUsada = IdempotencyKey.find("chave = ?1", idempotencyKey)
                .firstResultOptional().isPresent();
        if (chaveUsada) {
            return Response.status(409).entity("Requisição duplicada. Essa chave já foi usada.").build();
        }

        exercicio.persist(); // qualquer erro de validação será tratado no mapper global

        IdempotencyKey registro = new IdempotencyKey();
        registro.chave = idempotencyKey;
        registro.metodo = "POST";
        registro.endpoint = "/exercicios";
        registro.persist();

        URI location = URI.create("/exercicios/" + exercicio.id);
        return Response.created(location).entity(exercicio).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Exercicio exercicio) {
        Exercicio entidade = Exercicio.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        entidade.nome = exercicio.nome;
        entidade.descricao = exercicio.descricao;
        entidade.musculos_principais = exercicio.musculos_principais;
        entidade.musculos_secundarios = exercicio.musculos_secundarios;
        entidade.tipo = exercicio.tipo;
        entidade.instrucoes = exercicio.instrucoes;

        return Response.ok(entidade).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Exercicio.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Exercício com ID " + id + " não encontrado para exclusão.")
                .build();
    }


    @GET
    @Path("/busca/exercicio/{nome}")
    public List<Exercicio> buscarPorNome(@PathParam("nome") String nome) {
        return Exercicio.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }

}
