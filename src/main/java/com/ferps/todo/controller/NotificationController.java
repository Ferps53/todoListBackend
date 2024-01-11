package com.ferps.todo.controller;

import com.ferps.todo.dto.tarefa.TokenNotificacaoDTO;
import com.ferps.todo.mapper.FCMTokenMapper;
import com.ferps.todo.model.RegistroTokenNotificacao;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NotificationController {

    @Inject
    FCMTokenMapper fcmTokenMapper;

    @Startup
    public void iniciarFirebase() throws IOException {
        System.out.println("Iniciando o Firebase...");

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("/serviceFirebase.json");
        if(is != null){
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();
            FirebaseApp.initializeApp(options);
        }else {
            throw new IOException("Rapaiz");
        }

    }

    public BatchResponse enviarNotificacao(List<Message> messageList) throws FirebaseMessagingException {
        return FirebaseMessaging.getInstance().sendAll(messageList);
    }

    @Transactional
    public TokenNotificacaoDTO salvarTokenFCM(TokenNotificacaoDTO token, String idUsuario){
        Optional<RegistroTokenNotificacao> registroOp = RegistroTokenNotificacao.find("idUsuario", idUsuario)
                .singleResultOptional();

        new RegistroTokenNotificacao();
        RegistroTokenNotificacao registro;

        if(registroOp.isPresent()){
            registro = registroOp.get();
            registro.setFcmToken(token.getFcmToken());
        }else{
            registro = fcmTokenMapper.toRegistro(token);
        }
        registro.setIdUsuario(idUsuario);
        registro.persist();

        return fcmTokenMapper.toTokenDTO(registro);
    }


}
