package com.ferps.todo.controller;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.enums.UserRoles;
import com.ferps.todo.mapper.UsuarioMapper;
import com.ferps.todo.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CadastroController {

    @Inject
    UsuarioController usuarioController;

    @Inject
    UsuarioMapper usuarioMapper;

    @Transactional
    public Response cadastrarUsuario(UsuarioFrontDTO usuarioFrontDTO) {

        Usuario usuario = usuarioController.findUsuarioExistente(usuarioFrontDTO);

        if (usuario == null) {

           Usuario novoUsuario = usuarioMapper.toUsuario(usuarioFrontDTO);

            novoUsuario.persist();
            return Response.accepted(usuarioMapper.toUsuarioFrontDTO(novoUsuario)).build();
        }

        return Response.status(Response.Status.CONFLICT).entity("Nome de usuário ou email já cadastrados").build();
    }
}

