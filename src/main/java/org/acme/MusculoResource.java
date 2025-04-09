package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/musculos")
@Produces(MediaType.APPLICATION_JSON)

@Consumes(MediaType.APPLICATION_JSON)
public class MusculoResource {

    @GET
    public List<Musculo> listarTodos() {
        return Musculo.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Musculo musculo = Musculo.findById(id);
        if (musculo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(musculo).build();
    }

    @POST
    @Transactional
    public Response criar(Musculo musculo) {
        musculo.persist();
        return Response.created(URI.create("/musculos/" + musculo.id)).entity(musculo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Musculo musculo) {
        Musculo entidade = Musculo.findById(id);
        if (entidade == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/busca/musculo/{nome}")
    public List<Musculo> buscarPorNome(@PathParam("nome") String nome) {
        return Musculo.list("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%");
    }

}
