package com.ferps.todo.controller;


import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UsuarioController {

    /**
     * @param usuarioFrontDTO Usa o DTO do Usuário para verificar se este já existe no sistema
     * @return Se o usuário existir retorna o mesmo(Class Usuario), senão retorna null
     */
    public Usuario findUsuarioExistente(UsuarioFrontDTO usuarioFrontDTO){
        List<Usuario> listUsuarios = Usuario.findAll().list();
        Usuario usuarioEncontrado = null;

        for (Usuario usuario : listUsuarios) {
            if ((usuarioFrontDTO.getNomeUsuario().equals(usuario.getNome()) || usuarioFrontDTO.getEmail().equals(usuario.getEmail())) &&
                    usuarioFrontDTO.getSenha().equals(usuario.getSenha())) {
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }
}
