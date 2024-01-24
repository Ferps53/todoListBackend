package com.ferps.todo.endpoint;

import com.ferps.todo.controller.NotificationController;
import com.ferps.todo.dto.tarefa.TokenNotificacaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/notificacao")
public class NotificationEndpoint {

    @Inject
    NotificationController notificationController;

    @Inject
    @Claim(standard = Claims.sub)
    String idUsuario;


    @PUT
    @Path("/salvar/token")
    public Response salvarToken(TokenNotificacaoDTO token){
        TokenNotificacaoDTO tokenResponse = notificationController.salvarTokenFCM(token, idUsuario);
        return Response.ok(tokenResponse).build();
    }
}
