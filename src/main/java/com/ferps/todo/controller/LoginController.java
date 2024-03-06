package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.dto.token.TokenDTO;
import com.ferps.todo.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class LoginController {

    @Inject
    UsuarioController usuarioController;

    public Response login(UsuarioFrontDTO usuarioFrontDTO) {

        Usuario usuarioEncontrado = usuarioController.findUsuarioExistente(usuarioFrontDTO);

        if (usuarioEncontrado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário ou senha incorretos").build();
        }

        return Response.status(Response.Status.OK).entity(gerarToken(usuarioEncontrado)).build();
    }

    private TokenDTO gerarToken(Usuario usuario) {

        final int EXPIRACAO_TOKEN_EM_SEGUNDOS = 3600;

        String jwtToken = Jwt.issuer("Scheduler")
                .issuedAt(System.currentTimeMillis())
                .groups("Aluno") //Coloca esse cara aqui para settar o grupo
                .subject(usuario.getId().toString())
                .expiresAt(System.currentTimeMillis() + EXPIRACAO_TOKEN_EM_SEGUNDOS)
                .sign();

        return new TokenDTO(jwtToken, EXPIRACAO_TOKEN_EM_SEGUNDOS);
    }


}
