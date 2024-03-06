package com.ferps.todo.mapper;

import com.ferps.todo.dto.MidiaDTO;
import com.ferps.todo.model.Midia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface MidiaMapper {

    Midia toMidia(MidiaDTO midiaDTO);

    MidiaDTO toMidiaDTO(Midia midia);
}
