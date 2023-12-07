package com.ferps.todo.dto.UsuarioCadastro;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredenciaisDTO{

    String type;

    String value;

    boolean temporary;

}
