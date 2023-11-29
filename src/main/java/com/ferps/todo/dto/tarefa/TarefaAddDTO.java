package com.ferps.todo.dto.tarefa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaAddDTO {

    private String titulo;

    private String nomeUsuario;

    private String descricao;

    private String dataExpiracao;

}
