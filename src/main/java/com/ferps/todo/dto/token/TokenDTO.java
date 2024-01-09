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
    private String refreshToken;

    @JsonProperty("refresh_expires_in")
    private Integer tempoExpiracaoRefresh;

    public TokenDTO(String accessToken, Integer tempoExpiracao) {
        this.accessToken = accessToken;
        this.tempoExpiracao = tempoExpiracao;
    }
}
