package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.dto.token.TokenDTO;
import com.ferps.todo.restclient.LoginRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@ApplicationScoped
public class LoginController {

    @Inject
    @RestClient
    LoginRestClient loginRestClient;

    @ConfigProperty(name = "clientIdLogin")
    String clientId;

    public Response login(UsuarioFrontDTO usuarioFrontDTO) throws Throwable {

        try {
            return Response.ok(loginRestClient.getLoginToken(clientId, "password", usuarioFrontDTO.getNomeUsuario(), usuarioFrontDTO.getSenha())).build();
        } catch (ClientWebApplicationException e) {
            System.out.println(e.getMessage());


            throw e.getCause();
        }
    }
}
