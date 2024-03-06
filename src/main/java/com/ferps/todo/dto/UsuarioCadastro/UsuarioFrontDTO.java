package com.ferps.todo.dto.UsuarioCadastro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFrontDTO {

    String email;

    String nomeUsuario;

    String senha;

    Integer userRoles;

    Integer userType;

}
