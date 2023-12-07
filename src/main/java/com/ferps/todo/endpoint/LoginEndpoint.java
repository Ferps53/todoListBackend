package com.ferps.todo.endpoint;

import com.ferps.todo.controller.LoginController;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.filter.annotation.TipoSessao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/login")
public class LoginEndpoint {

    @Inject
    LoginController loginController;

    @POST
    @TipoSessao
    public Response login(UsuarioFrontDTO usuarioFrontDTO){
        return Response.ok(loginController.login(usuarioFrontDTO)).build();
    }
}
