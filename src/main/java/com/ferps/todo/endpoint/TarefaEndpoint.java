package com.ferps.todo.endpoint;

import com.ferps.todo.controller.TarefaController;
import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/tarefa")
@RequestScoped
@SecurityScheme(securitySchemeName = "oauth-todoList", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl =
                "http://localhost:8180/auth/realms/TodoList/protocol/openid-connect/token")))
@SecurityRequirement(name = "oauth-todoList")
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

    @POST
    public Response createTarefa(TarefaAddDTO tarefaAddDTO){
        return Response.ok(tarefaController.createTarefa(tarefaAddDTO, idUsuario)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateTarefa(@PathParam("id") Integer idTarefa, TarefaFrontDTO tarefaFrontDTO){
        return Response.ok(tarefaController.updateTarefa(tarefaFrontDTO, idTarefa)).build();
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
