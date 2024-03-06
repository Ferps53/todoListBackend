package com.ferps.todo.model;

import com.ferps.todo.enums.Type;
import com.ferps.todo.enums.converter.TypeConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class InteressadosMidias extends PanacheEntityBase implements Serializable {

    @Id
    @ManyToOne
    private Interessado interessado;

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private Midia midia;

    @Column
    @Convert(converter = TypeConverter.class)
    private Type type;

}
