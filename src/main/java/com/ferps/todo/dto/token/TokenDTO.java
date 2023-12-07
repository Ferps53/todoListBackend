package com.ferps.todo.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer tempoExpiracao;

    @JsonProperty("refresh_token")
    private Integer refreshToken;

    @JsonProperty("refresh_expires_in")
    private Integer tempoExpiracaoRefresh;

}
