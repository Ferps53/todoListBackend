package com.ferps.todo.mapper;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {

    @Mapping(target = "nome", source = "usuarioFrontDTO.nomeUsuario")
    Usuario toUsuario(UsuarioFrontDTO usuarioFrontDTO);

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    UsuarioFrontDTO toUsuarioFrontDTO(Usuario usuario);

}
