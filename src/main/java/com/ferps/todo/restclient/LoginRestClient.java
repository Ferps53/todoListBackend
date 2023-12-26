package com.ferps.todo.restclient;

import com.ferps.todo.dto.token.TokenDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-token")
public interface LoginRestClient {

    @Path("/token")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    TokenDTO getLoginToken(@FormParam("client_id") String client,
                           @FormParam("grant_type") String grant,
                           @FormParam("username") String username,
                           @FormParam("password") String password);
}
