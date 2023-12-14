package com.ferps.todo.endpoint;

import com.ferps.todo.controller.NotificationController;
import com.ferps.todo.filter.annotation.SessaoPublica;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Produces(MediaType.TEXT_PLAIN)
@RequestScoped
@Path("/notificacao")
public class NotificationEndpoint {

    @Inject
    NotificationController notificationController;
    @GET
    @SessaoPublica
    public void enviarNotif() throws FirebaseMessagingException, IOException {
        notificationController.enviarNotificacao();
    }

}
