package com.ferps.todo.controller;

import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaConcluirDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.mapper.TarefaMapper;
import com.ferps.todo.model.Tarefa;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.*;

@ApplicationScoped
@Transactional
public class TarefaController {

    @Inject
    TarefaMapper tarefaMapper;

    public List<TarefaFrontDTO> getTarefas(String usuario){
         List<Tarefa> listTarefa = Tarefa.find("idUsuario", usuario).list();

         return tarefaMapper.tolistTarefaDTO(listTarefa);
    }

    public TarefaFrontDTO getTarefaUnica(Integer idTarefa, String usuario){
        Map<String, Object>params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("idTarefa", idTarefa);

        Tarefa tarefa = (Tarefa) Tarefa.find(
                "select t from Tarefa where nomeUsuario = :usuario and id = :idTarefa order by data_expiracao asc"
                , params);

        return tarefaMapper.toTarefaDTO(tarefa);
    }


    public TarefaFrontDTO createTarefa(TarefaAddDTO tarefaAddDTO, String usuario){
        Tarefa tarefa =  tarefaMapper.toTarefa(tarefaAddDTO);
        tarefa.setIdUsuario(usuario);
        tarefa.persist();
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateTarefa(TarefaFrontDTO tarefaFrontDTO, Integer idTarefa){
        Optional<Tarefa> tarefaOp = Tarefa.findByIdOptional(idTarefa);
        if(tarefaOp.isEmpty()){
            throw new NotFoundException("Sem tarefa");
        }
        Tarefa tarefa = tarefaOp.get();
        tarefaMapper.toTarefa(tarefaFrontDTO, tarefa);
        tarefa.persist();
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public void deleteTarefa(Integer idTarefa){
        Optional<Tarefa> tarefaOp = Tarefa.findByIdOptional(idTarefa);
        if(tarefaOp.isEmpty()){
            throw new NotFoundException("Sem tarefa");
        }
        tarefaOp.get().delete();

    }


}
