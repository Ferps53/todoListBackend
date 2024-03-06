package com.ferps.todo.filter;

import com.ferps.todo.filter.annotation.SessaoPublica;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class FiltroSessao implements ContainerRequestFilter {

    @Context
    ResourceInfo info;

    @Override
    public void filter(ContainerRequestContext requestContext){
        if(!info.getResourceMethod().isAnnotationPresent(SessaoPublica.class)){
            validaToken(requestContext);
        }
    }

    public void validaToken(ContainerRequestContext requestContext){
        String token = requestContext.getHeaderString("Authorization");
        if(token == null || token.isEmpty()){
            requestContext.abortWith(Response.ok("Token not found").status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build());
        } else if(!token.startsWith("Bearer ")){
            requestContext.abortWith(Response.ok("Invalid Token").status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build());
        } else if (token.replace("Bearer ", "").trim().isEmpty()) {
            requestContext.abortWith(Response.ok("Token not Found").status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build());
        }
    }
}
