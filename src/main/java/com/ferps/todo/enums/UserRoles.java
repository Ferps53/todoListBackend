package com.ferps.todo.enums;

public enum UserRoles {

    ADMIN("admin"),
    ALUNO("aluno"),
    PROFESSOR("professor");

    final String role;

    UserRoles(String role) {
        this.role = role;
    }

    public static String getRoles(UserRoles userRoles){
        return switch (userRoles){
            case ADMIN -> "admin";
            case ALUNO -> "aluno";
            case PROFESSOR -> "professor";
        };
    }
}
