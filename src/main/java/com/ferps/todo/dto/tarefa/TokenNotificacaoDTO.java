package com.ferps.todo.dto.tarefa;

import lombok.Data;

@Data
public class TokenNotificacaoDTO {

    Long id;

    String idUsuario;

    String fcmToken;

}
