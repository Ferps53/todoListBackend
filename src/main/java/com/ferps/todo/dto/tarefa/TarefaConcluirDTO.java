package com.ferps.todo.dto.tarefa;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaConcluirDTO {

    private Boolean fgConcluida;

    private String dataConclusao;

}
