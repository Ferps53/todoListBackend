package com.ferps.todo.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NotificationController {

    public void iniciarFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("/home/felipe/Documentos/Prog/todoListBackend/src/main/resources/serviceFirebase.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }


    public void enviarNotificacao() throws FirebaseMessagingException {

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Albion Online")
                            .setBody("O mmo rpg sandbox de mundo aberto!")
                            .build())
                    .setToken("fzqbiL0XQvC-Wq9cSGNmUC:APA91bFJO-7mHH68wPabRxL7EH_-ZMP92p6nxWmw8wa17xE2vKDcoGTAb1MbGws2mBeH_3apnjh84FWQGfx9k7v-zPI93Ocn_XnM4FNVVpZNJWqDBWACSerffORyYMf_n1vU6R0OuibL")
                    .build();
            messageList.add(message);
        }
        BatchResponse response = FirebaseMessaging.getInstance().sendAll(messageList);
        System.out.println(response.getResponses());

    }


}
