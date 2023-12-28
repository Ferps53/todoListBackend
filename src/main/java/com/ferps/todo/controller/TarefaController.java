package com.ferps.todo.controller;

import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.mapper.TarefaMapper;
import com.ferps.todo.model.Tarefa;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class TarefaController {

    @Inject
    TarefaMapper tarefaMapper;

    public List<TarefaFrontDTO> getTarefas(String usuario){
        List<Tarefa> listTarefa = Tarefa.find("idUsuario", Sort.by("dataExpiracao", Sort.Direction.Ascending), usuario).list();

        return tarefaMapper.tolistTarefaDTO(listTarefa);
    }

    public TarefaFrontDTO getTarefaUnica(Integer idTarefa, String usuario){
       verifcarSeTarefaExiste(idTarefa);

        Map<String, Object>params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("idTarefa", idTarefa);

        Tarefa tarefa = Tarefa.find(
                "idUsuario = :usuario and id = :idTarefa"
                , params).firstResult();

        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO createTarefa(TarefaAddDTO tarefaAddDTO, String usuario){
        Tarefa tarefa =  tarefaMapper.toTarefa(tarefaAddDTO);
        tarefa.setIdUsuario(usuario);
        tarefa.setFgConcluida(false);
        tarefa.persist();
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateTarefa(TarefaFrontDTO tarefaFrontDTO, Integer idTarefa){

        Tarefa tarefa =  verifcarSeTarefaExiste(idTarefa);

        tarefa.setTitulo(tarefaFrontDTO.getTitulo());
        tarefa.setDescricao(tarefaFrontDTO.getDescricao());
        tarefa.setDataExpiracao(LocalDateTime.parse(tarefaFrontDTO.getDataExpiracao()));

        tarefa.persist();
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateStatus(TarefaFrontDTO tarefaFrontDTO, Integer idTarefa){

        Tarefa tarefa = verifcarSeTarefaExiste(idTarefa);

        tarefa.setFgConcluida(tarefaFrontDTO.getFgConcluida());
        tarefa.persist();

        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public void deleteTarefa(Integer idTarefa){
        verifcarSeTarefaExiste(idTarefa).delete();
    }

    private Tarefa verifcarSeTarefaExiste(Integer idTarefa){
        Optional<Tarefa> tarefaOp = Tarefa.findByIdOptional(idTarefa);
        if(tarefaOp.isEmpty()){
            throw new NotFoundException("Sem tarefa");
        }
        return tarefaOp.get();
    }

}
