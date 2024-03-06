package com.ferps.todo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Interessado extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInteressado;

    private String nome;

    @OneToMany(mappedBy = "interessado", cascade = CascadeType.ALL)
    private List<InteressadosMidias> listInteressadosMidias;

}
