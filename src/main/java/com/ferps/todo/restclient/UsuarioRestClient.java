package com.ferps.todo.restclient;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioCadastroKeycloakDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "usuarioRest")
public interface UsuarioRestClient {

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    Response cadastrarUsuario(@HeaderParam("Authorization")String token, UsuarioCadastroKeycloakDTO usuarioCadastroDTO);
}
