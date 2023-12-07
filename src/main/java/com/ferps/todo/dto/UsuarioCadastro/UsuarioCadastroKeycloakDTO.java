package com.ferps.todo.dto.UsuarioCadastro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroKeycloakDTO {

    String email;

    String username;

    @JsonProperty("credentials")
    List<CredenciaisDTO> credenciaisDTO;

    List<String> realmRoles;

    boolean enabled;

}
