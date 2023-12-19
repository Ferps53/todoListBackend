package com.ferps.todo.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registro_token_notif")
public class RegistroTokenNotificacao extends PanacheEntityBase {

    @Id
    @Column(name="id_registro_token")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="id_usuario")
    String idUsuario;

    @Column(name="fcm_token")
    String fcmToken;

}


