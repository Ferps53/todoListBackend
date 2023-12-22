package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.CredenciaisDTO;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioCadastroKeycloakDTO;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.dto.token.TokenDTO;
import com.ferps.todo.restclient.CadastroKeycloakRestClient;
import com.ferps.todo.restclient.UsuarioRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CadastroController {

    @Inject
    @RestClient
    UsuarioRestClient usuarioRestClient;

    @ConfigProperty(name = "clientIdCadastro")
    String clientId;

    @ConfigProperty(name = "clientSecretCadastro")
    String secret;

    @RestClient
    @Inject
    CadastroKeycloakRestClient cadastroRestClient;


    public Response cadastrarUsuario(UsuarioFrontDTO usuarioFront) {
        String grant = "client_credentials";

        TokenDTO jsonInteiro = cadastroRestClient.getCredenciaisAdmin(secret, clientId, grant);

        String token = "Bearer " + jsonInteiro.getAccessToken();

        UsuarioCadastroKeycloakDTO usuarioCadastro = getUsuarioCadastroKeycloakDTO(usuarioFront);

        try{
            Response responseCadastro = usuarioRestClient.cadastrarUsuario(token, usuarioCadastro);
            return Response.status(responseCadastro.getStatus()).build();
        }catch (ClientWebApplicationException e){
            return e.getResponse();
        }


    }

    private static UsuarioCadastroKeycloakDTO getUsuarioCadastroKeycloakDTO(UsuarioFrontDTO usuarioFront) {
        CredenciaisDTO credenciais = new CredenciaisDTO("password",
                usuarioFront.getSenha(),
                false);

        List<String> listRoles = new ArrayList<>();
        List<CredenciaisDTO> listCredentials = new ArrayList<>();
        listCredentials.add(credenciais);

        listRoles.add("usuario");

        return new UsuarioCadastroKeycloakDTO(
                usuarioFront.getEmail(), usuarioFront.getNomeUsuario(),
                listCredentials, listRoles,true
        );
    }


}
