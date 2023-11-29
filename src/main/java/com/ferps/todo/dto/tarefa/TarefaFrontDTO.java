package com.ferps.todo.dto.tarefa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaFrontDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private Boolean fgConcluida;

    private String dataCriacao;

    private String dataExpiracao;

}
