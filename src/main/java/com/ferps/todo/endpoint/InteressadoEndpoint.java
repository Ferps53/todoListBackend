package com.ferps.todo.endpoint;

import com.ferps.todo.controller.InteressadoController;
import com.ferps.todo.filter.annotation.SessaoPublica;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/interessado")
public class InteressadoEndpoint {

    @Inject
    InteressadoController interessadoController;

    @GET
    @SessaoPublica
    public Response getInteressados(){
        return Response.ok(interessadoController.listInteressados()).build();
    }

}
