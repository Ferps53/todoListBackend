package com.ferps.todo.restclient;

import com.ferps.todo.dto.token.TokenDTO;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-token")
public interface LoginRestClient {

    @Path("/token")
    @POST
    TokenDTO getLoginToken(@FormParam("client_id") String client,
                           @FormParam("grant_type") String grant,
                           @FormParam("username") String username,
                           @FormParam("password") String password);
}
