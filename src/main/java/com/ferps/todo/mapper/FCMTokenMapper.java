package com.ferps.todo.mapper;

import com.ferps.todo.dto.tarefa.TokenNotificacaoDTO;
import com.ferps.todo.model.RegistroTokenNotificacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface FCMTokenMapper {

    TokenNotificacaoDTO toTokenDTO(RegistroTokenNotificacao registro);

    RegistroTokenNotificacao toRegistro(TokenNotificacaoDTO dto);

}
