package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/treinos")
@Produces(MediaType.APPLICATION_JSON)

@Consumes(MediaType.APPLICATION_JSON)
public class TreinoResource {

    @GET
    public List<Treino> listarTodos() {
        return Treino.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Treino treino = Treino.findById(id);
        if (treino == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(treino).build();
    }

    @POST
    @Transactional
    public Response criar(Treino treino) {
        treino.persist();
        return Response.created(URI.create("/treinos/" + treino.id)).entity(treino).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Treino treino) {
        Treino entidade = Treino.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/busca/treino/{nome}")
    public List<Treino> buscarPorNome(@PathParam("nome") String nome) {
        return Treino.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }

}
