package com.ferps.todo.restclient;

import com.ferps.todo.dto.token.TokenDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-cadastro")
public interface CadastroKeycloakRestClient {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    TokenDTO getCredenciaisAdmin(@FormParam("client_secret") String secret,
                                 @FormParam("client_id") String client,
                                 @FormParam("grant_type") String grant);
}
