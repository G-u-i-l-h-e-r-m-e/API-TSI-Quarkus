package org.acme;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;

import java.net.URI;
import java.util.List;

@Path("/musculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusculoResource {

    @GET
    @RateLimit(value = 3, window = 10) // 3 requisições a cada 10 segundos
    @Fallback(fallbackMethod = "rateLimitFallback")
    public Response listarTodos() {
        return Response.ok(Musculo.listAll()).build();
    }

    public Response rateLimitFallback() {
        return Response.status(429)
                .entity("⚠️ Limite de requisições atingido. Tente novamente mais tarde.")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Musculo musculo = Musculo.findById(id);
        if (musculo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Musculo com ID " + id + " não encontrado.")
                    .build();
        }
        return Response.ok(musculo).build();
    }

    @POST
    @Transactional
    public Response criar(
            Musculo musculo,
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

        // Erros de validação agora são tratados globalmente
        musculo.persist();

        IdempotencyKey registro = new IdempotencyKey();
        registro.chave = idempotencyKey;
        registro.metodo = "POST";
        registro.endpoint = "/musculos";
        registro.persist();

        URI location = URI.create("/musculos/" + musculo.id);
        return Response.created(location).entity(musculo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Musculo musculo) {
        Musculo entidade = Musculo.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Musculo com ID " + id + " não encontrado para atualização.")
                    .build();
        }

        entidade.nome = musculo.nome;
        entidade.descricao = musculo.descricao;
        entidade.grupo_muscular = musculo.grupo_muscular;

        return Response.ok(entidade).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Musculo.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Musculo com ID " + id + " não encontrado para exclusão.")
                .build();
    }

    @GET
    @Path("/busca/musculo/{nome}")
    public List<Musculo> buscarPorNome(@PathParam("nome") String nome) {
        return Musculo.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }
}
