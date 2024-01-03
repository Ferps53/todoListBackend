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

    private String idUsuario;

    private String descricao;

    private Boolean fgConcluida;

    private Boolean fgLixeira;

    private String dataEnvioLixeira;

    private String dataCriacao;

    private String dataExpiracao;

    private String dataConclusao;

}
