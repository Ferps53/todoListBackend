package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.dto.token.TokenDTO;
import com.ferps.todo.restclient.LoginRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class LoginController {

    @Inject
    @RestClient
    LoginRestClient loginRestClient;

    @ConfigProperty(name = "clientIdLogin")
    String clientId;

    public TokenDTO login(UsuarioFrontDTO usuarioFrontDTO){

       return loginRestClient.getLoginToken(clientId, "password", usuarioFrontDTO.getNomeUsuario(), usuarioFrontDTO.getSenha());

    }


}
