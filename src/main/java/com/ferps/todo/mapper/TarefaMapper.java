package com.ferps.todo.mapper;

import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.model.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TarefaMapper {

    Tarefa toTarefa(TarefaFrontDTO tarefaFrontDTO);

    void toTarefa(TarefaFrontDTO tarefaFrontDTO, @MappingTarget Tarefa tarefa);

    Tarefa toTarefa(TarefaAddDTO tarefaAddDTO);

    TarefaFrontDTO toTarefaDTO(Tarefa tarefa);

    List<TarefaFrontDTO> tolistTarefaDTO(List<Tarefa> listTarefa);

}
