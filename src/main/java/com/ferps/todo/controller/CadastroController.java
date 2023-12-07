package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.CredenciaisDTO;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioCadastroKeycloakDTO;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontCadastroDTO;
import com.ferps.todo.dto.token.TokenDTO;
import com.ferps.todo.filter.annotation.TipoSessao;
import com.ferps.todo.restclient.CadastroKeycloakRestClient;
import com.ferps.todo.restclient.UsuarioRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@TipoSessao
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


    public Response cadastrarUsuario(UsuarioFrontCadastroDTO usuarioFront){
        String grant = "client_credentials";

        TokenDTO jsonInteiro = cadastroRestClient.getCredenciaisAdmin(secret, clientId, grant);

        String token = "Bearer " + jsonInteiro.getAccessToken();

        UsuarioCadastroKeycloakDTO usuarioCadastro = getUsuarioCadastroKeycloakDTO(usuarioFront);

        return usuarioRestClient.cadastrarUsuario(token, usuarioCadastro);

    }

    private static UsuarioCadastroKeycloakDTO getUsuarioCadastroKeycloakDTO(UsuarioFrontCadastroDTO usuarioFront) {
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
