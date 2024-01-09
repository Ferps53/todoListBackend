package com.ferps.todo.endpoint;

import com.ferps.todo.controller.CadastroController;
import com.ferps.todo.controller.LoginController;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontDTO;
import com.ferps.todo.filter.annotation.SessaoPublica;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class UsuarioEndpoint {

    @Inject
    LoginController loginController;

    @Inject
    CadastroController cadastroController;

    @POST
    @SessaoPublica
    @Path("/login")
    public Response login(UsuarioFrontDTO usuarioFrontDTO) {
        return loginController.login(usuarioFrontDTO);
    }

    @POST
    @SessaoPublica
    @Path("/cadastro")
    public Response cadastrar(UsuarioFrontDTO usuarioFront) {
        return cadastroController.cadastrarUsuario(usuarioFront);
    }
}
