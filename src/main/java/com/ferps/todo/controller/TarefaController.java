package com.ferps.todo.controller;

import com.ferps.todo.dto.tarefa.TarefaAddDTO;
import com.ferps.todo.dto.tarefa.TarefaConcluirDTO;
import com.ferps.todo.dto.tarefa.TarefaFrontDTO;
import com.ferps.todo.dto.tarefa.TarefaLixeiraDTO;
import com.ferps.todo.mapper.TarefaMapper;
import com.ferps.todo.model.Tarefa;
import com.ferps.todo.redis.RedisCacher;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@ApplicationScoped
@Transactional
public class TarefaController {

    @Inject
    TarefaMapper tarefaMapper;

    @Inject
    RedisCacher redisCacher;

    public List<TarefaFrontDTO> getTarefas(String usuario) {
        List<TarefaFrontDTO> dtoList = redisCacher.getListFromCache("tarefas:" + usuario, TarefaFrontDTO.class);

        if (dtoList == null || dtoList.isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            params.put("usuario", usuario);

            List<Tarefa> listTarefa = Tarefa.find("idUsuario = :usuario and fgLixeira = false", Sort.by("dataExpiracao", Sort.Direction.Ascending), params).list();

            redisCacher.saveInCache("tarefas:" + usuario, tarefaMapper.tolistTarefaDTO(listTarefa));

            return tarefaMapper.tolistTarefaDTO(listTarefa);
        }
        return dtoList;
    }

    public List<TarefaFrontDTO> getTarefasLixeira(String usuario) {
        List<TarefaFrontDTO> listTarefasLixeira = redisCacher.getListFromCache("tarefas:lixeira:" + usuario, TarefaFrontDTO.class);

        if(listTarefasLixeira == null || listTarefasLixeira.isEmpty()){
            Map<String, Object> params = new HashMap<>();
            params.put("usuario", usuario);

            List<Tarefa> listTarefa = Tarefa.find("idUsuario = :usuario and fgLixeira = true", Sort.by("dataExpiracao", Sort.Direction.Ascending), params).list();

            redisCacher.saveInCache("tarefas:lixeira:"+usuario, tarefaMapper.tolistTarefaDTO(listTarefa));
            return tarefaMapper.tolistTarefaDTO(listTarefa);
        }
        return listTarefasLixeira;
    }

    public TarefaFrontDTO getTarefaUnica(Integer idTarefa, String usuario) {
        verifcarSeTarefaExiste(idTarefa);
        TarefaFrontDTO tarefaCache = redisCacher.getFromCache("tarefas:id"+idTarefa+":"+usuario, TarefaFrontDTO.class);

        if(tarefaCache == null){
            Map<String, Object> params = new HashMap<>();
            params.put("usuario", usuario);
            params.put("idTarefa", idTarefa);

            Tarefa tarefa = Tarefa.find(
                    "idUsuario = :usuario and id = :idTarefa"
                    , params).firstResult();

            return tarefaMapper.toTarefaDTO(tarefa);
        }
        return tarefaCache;
    }

    public TarefaFrontDTO createTarefa(TarefaAddDTO tarefaAddDTO, String usuario) {
        Tarefa tarefa = tarefaMapper.toTarefa(tarefaAddDTO);
        tarefa.setIdUsuario(usuario);
        tarefa.setFgConcluida(false);
        tarefa.setFgLixeira(false);
        tarefa.persist();
        redisCacher.deleteKey("tarefas:"+usuario);
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateTarefa(TarefaFrontDTO tarefaFrontDTO, Integer idTarefa) {

        Tarefa tarefa = verifcarSeTarefaExiste(idTarefa);

        tarefa.setTitulo(tarefaFrontDTO.getTitulo());
        tarefa.setDescricao(tarefaFrontDTO.getDescricao());
        tarefa.setDataExpiracao(LocalDateTime.parse(tarefaFrontDTO.getDataExpiracao()));

        tarefa.persist();
        redisCacher.deleteKey("tarefas:"+tarefa.getIdUsuario());
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateStatus(TarefaConcluirDTO tarefaFrontDTO, Integer idTarefa) {
        Tarefa tarefa = verifcarSeTarefaExiste(idTarefa);
        if (tarefaFrontDTO.getDataConclusao() == null) {
            tarefa.setDataConclusao(null);
        } else {
            tarefa.setDataConclusao(LocalDateTime.parse(tarefaFrontDTO.getDataConclusao()));
        }

        tarefa.setFgConcluida(tarefaFrontDTO.getFgConcluida());
        tarefa.persist();
        redisCacher.deleteKey("tarefas:"+tarefa.getIdUsuario());
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public TarefaFrontDTO updateStatusLixeira(TarefaLixeiraDTO tarefaLixeiraDTO, Integer idTarefa) {
        Tarefa tarefa = verifcarSeTarefaExiste(idTarefa);
        if (tarefaLixeiraDTO.getDataEnvioLixeira() != null) {
            tarefa.setDataEnvioLixeira(LocalDateTime.parse(tarefaLixeiraDTO.getDataEnvioLixeira()));
        }
        tarefa.setFgLixeira(tarefaLixeiraDTO.getFgLixeira());
        tarefa.persist();
        redisCacher.deleteKey("tarefas:"+tarefa.getIdUsuario());
        redisCacher.deleteKey("tarefas:lixeira"+tarefa.getIdUsuario());
        return tarefaMapper.toTarefaDTO(tarefa);
    }

    public void deleteTarefa(Integer idTarefa) {
        Tarefa tarefa = verifcarSeTarefaExiste(idTarefa);
        redisCacher.deleteKey("tarefas:"+tarefa.getIdUsuario());
        redisCacher.deleteKey("tarefas:lixeira"+tarefa.getIdUsuario());
        redisCacher.deleteKey("tarefas"+idTarefa+":"+tarefa.getIdUsuario());
        tarefa.delete();
    }

    private Tarefa verifcarSeTarefaExiste(Integer idTarefa) {
        Optional<Tarefa> tarefaOp = Tarefa.findByIdOptional(idTarefa);
        if (tarefaOp.isEmpty()) {
            throw new NotFoundException("Sem tarefa");
        }
        return tarefaOp.get();
    }

}
