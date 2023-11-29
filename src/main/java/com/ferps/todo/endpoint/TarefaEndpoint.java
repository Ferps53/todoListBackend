package com.ferps.todo.endpoint;

import com.ferps.todo.controller.TarefaController;
import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/tarefa")
public class TarefaEndpoint {

    @Inject
    TarefaController tarefaController;

    @GET
    public Response getTarefas(){
        return Response.ok(tarefaController.getTarefas()).build();
    }

    @POST
    public Response createTarefa(TarefaAddDTO tarefaAddDTO){
        return Response.ok(tarefaController.createTarefa(tarefaAddDTO)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateTarefa(@PathParam("id") Integer idTarefa, TarefaFrontDTO tarefaFrontDTO){
        return Response.ok(tarefaController.updateTarefa(tarefaFrontDTO, idTarefa)).build();
    }

    @DELETE
    public Response deleteTarefa(@PathParam("id") Integer idTarefa){
        tarefaController.deleteTarefa(idTarefa);
        return Response.ok().build();
    }


}
