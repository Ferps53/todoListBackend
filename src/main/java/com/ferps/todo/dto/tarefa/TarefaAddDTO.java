package com.ferps.todo.dto.tarefa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaAddDTO {

    private String titulo;

    private String descricao;

    private LocalDateTime dataExpiracao;

}
