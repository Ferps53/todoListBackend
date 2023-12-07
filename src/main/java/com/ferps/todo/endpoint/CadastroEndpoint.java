package com.ferps.todo.endpoint;

import com.ferps.todo.controller.CadastroController;
import com.ferps.todo.dto.UsuarioCadastro.UsuarioFrontCadastroDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/cadastro")
@RequestScoped
public class CadastroEndpoint {

    @Inject
    CadastroController cadastroController;

    @POST
    public Response cadastrar(UsuarioFrontCadastroDTO usuarioFront){
        return cadastroController.cadastrarUsuario(usuarioFront);
    }



}
