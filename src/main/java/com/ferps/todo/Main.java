package com.ferps.todo;

import com.ferps.todo.controller.NotificationController;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {

    public static void main(String[] args){
        NotificationController notificationController = new NotificationController();
        try {
            notificationController.iniciarFirebase();
        } catch (Exception e){
            e.printStackTrace();
        }
        Quarkus.run();
    }

}
