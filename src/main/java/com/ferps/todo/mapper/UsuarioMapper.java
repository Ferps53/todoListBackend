package com.ferps.todo.mapper;

import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.enums.Type;
import com.ferps.todo.enums.UserRoles;
import com.ferps.todo.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {

    default Integer mapRoles(UserRoles value) {return value.ordinal();}

    default UserRoles mapRoles(int value) {return UserRoles.values()[value];}

    default Integer mapType(Type type) {return type.ordinal();}
    default Type mapType(int value) {return Type.values()[value];}


    @Mapping(target = "nome", source = "usuarioFrontDTO.nomeUsuario")
    Usuario toUsuario(UsuarioFrontDTO usuarioFrontDTO);

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "userType", source = "usuario.userType")
    UsuarioFrontDTO toUsuarioFrontDTO(Usuario usuario);

}
