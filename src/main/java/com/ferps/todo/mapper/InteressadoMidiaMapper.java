package com.ferps.todo.mapper;

import com.ferps.todo.dto.InteressadoMidiaDTO;
import com.ferps.todo.enums.Type;
import com.ferps.todo.model.InteressadosMidias;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi", uses = {MidiaMapper.class})
public interface InteressadoMidiaMapper {

    default Integer mapType(Type type) {return type.ordinal();}
    default Type mapType(int value) {return Type.values()[value];}
    @Mappings({@Mapping(target = "idInteressado", source = "interessadosMidias.interessado.idInteressado"),
                @Mapping(target = "midiaDTO", source = "interessadosMidias.midia")
    })
    InteressadoMidiaDTO toInteressadoMidiaDTO(InteressadosMidias interessadosMidias);


    List<InteressadoMidiaDTO> toListInteressadoMidiaDTO(List<InteressadosMidias> listInteressadosMidia);

}
