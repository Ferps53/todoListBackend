package com.ferps.todo.mapper;

import com.ferps.todo.dto.InteressadoDTO;
import com.ferps.todo.enums.Type;
import com.ferps.todo.model.Interessado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi", uses = {InteressadoMidiaMapper.class})
public interface InteressadoMapper {

    @Mapping(target = "id", source = "interessado.idInteressado")
    InteressadoDTO toInteressadoDTO(Interessado interessado);

    List<InteressadoDTO> toInteressadoDTOList(List<Interessado> listInteressado);
}
