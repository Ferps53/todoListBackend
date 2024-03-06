package com.ferps.todo.model;

import com.ferps.todo.enums.Type;
import com.ferps.todo.enums.UserRoles;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "usuario")
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    @Password
    private String senha;

    @Column(name = "roles")
    private UserRoles userRoles;

    @Column(name = "type")
    private Type userType;

    @CreationTimestamp
    @Column(name = "data_criacao_conta")
    private LocalDateTime dataCriacaoConta;


    public Usuario(String nome, String email, String senha, UserRoles roles, Type type) {
        this.nome = nome;
        this.email = email;
        this.senha = BcryptUtil.bcryptHash(senha);
        this.userRoles = roles;
        this.userType = type;
    }

    public Usuario() {}
}
