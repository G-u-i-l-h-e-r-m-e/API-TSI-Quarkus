package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/exercicios")
@Produces(MediaType.APPLICATION_JSON)

@Consumes(MediaType.APPLICATION_JSON)
public class ExercicioResource {

    @GET
    public List<Exercicio> listarTodos() {
        return Exercicio.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Exercicio exercicio = Exercicio.findById(id);
        if (exercicio == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(exercicio).build();
    }

    @POST
    @Transactional
    public Response criar(Exercicio exercicio) {
        exercicio.persist();
        return Response.created(URI.create("/exercicios/" + exercicio.id)).entity(exercicio).build();
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
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/busca/exercicio/{nome}")
    public List<Exercicio> buscarPorNome(@PathParam("nome") String nome) {
        return Exercicio.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }

}
