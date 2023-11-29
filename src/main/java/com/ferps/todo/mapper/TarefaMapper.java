package com.ferps.todo.mapper;

import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.model.Tarefa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TarefaMapper {

    Tarefa toTarefa(TarefaFrontDTO tarefaFrontDTO);

    Tarefa toTarefa(TarefaFrontDTO tarefaFrontDTO, Tarefa tarefa);

    Tarefa toTarefa(TarefaAddDTO tarefaAddDTO);

    TarefaFrontDTO toTarefaDTO(Tarefa tarefa);

    List<TarefaFrontDTO> tolistTarefaDTO(List<Tarefa> listTarefa);

}
