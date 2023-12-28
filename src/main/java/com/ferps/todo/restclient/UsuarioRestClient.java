package com.ferps.todo.restclient;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioCadastroKeycloakDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-cadastro")
public interface UsuarioRestClient {

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response cadastrarUsuario(@HeaderParam("Authorization")String token, UsuarioCadastroKeycloakDTO usuarioCadastroDTO);
}
