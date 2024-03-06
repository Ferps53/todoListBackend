package com.ferps.todo.dto;

import lombok.Data;

import java.util.List;

@Data
public class InteressadoDTO {
    private Long id;

    private String nome;

    private List<InteressadoMidiaDTO> listInteressadosMidias;

}
