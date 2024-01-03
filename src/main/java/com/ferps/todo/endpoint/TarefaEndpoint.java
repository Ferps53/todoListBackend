package com.ferps.todo.endpoint;

import com.ferps.todo.controller.TarefaController;
import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaConcluirDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.dto.tarefa.TarefaLixeiraDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/tarefa")
@RequestScoped
public class TarefaEndpoint {

    @Inject
    TarefaController tarefaController;

    @Inject
    @Claim(standard = Claims.sub)
    String idUsuario;

    @GET
    public Response getTarefas(){
        return Response.ok(tarefaController.getTarefas(idUsuario)).build();
    }

    @GET
    @Path("/lixeira")
    public Response getTarefasLixeira(){
        return Response.ok(tarefaController.getTarefasLixeira(idUsuario)).build();
    }

    @POST
    public Response createTarefa(TarefaAddDTO tarefaAddDTO){
        return Response.ok(tarefaController.createTarefa(tarefaAddDTO, idUsuario)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateTarefa(@PathParam("id") Integer idTarefa, TarefaFrontDTO tarefaFrontDTO){
        return Response.ok(tarefaController.updateTarefa(tarefaFrontDTO, idTarefa)).build();
    }

    @PUT
    @Path("{id}/atualizarStatus")
    public Response atualizarStatus(@PathParam("id") Integer idTarefa, TarefaConcluirDTO tarefaFrontDTO){
        return Response.ok(tarefaController.updateStatus(tarefaFrontDTO, idTarefa)).build();
    }

    @PUT
    @Path("{id}/atualizarStatusLixeira")
    public Response atualizarStatusLixeira(@PathParam("id") Integer idTarefa, TarefaLixeiraDTO tarefaLixeiraDTO){
        return Response.ok(tarefaController.updateStatusLixeira(tarefaLixeiraDTO, idTarefa)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTarefa(@PathParam("id") Integer idTarefa){
        tarefaController.deleteTarefa(idTarefa);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Response getTarefaUnica(@PathParam("id") Integer idTarefa){
        return Response.ok(tarefaController.getTarefaUnica(idTarefa, idUsuario)).build();
    }
}
