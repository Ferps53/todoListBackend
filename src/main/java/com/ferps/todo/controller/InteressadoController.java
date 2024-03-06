package com.ferps.todo.controller;

import com.ferps.todo.dto.InteressadoDTO;
import com.ferps.todo.mapper.InteressadoMapper;
import com.ferps.todo.model.Interessado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class InteressadoController {

    @Inject
    InteressadoMapper interessadoMapper;

    public List<InteressadoDTO> listInteressados(){
     return interessadoMapper.toInteressadoDTOList(Interessado.listAll());
    }
}
