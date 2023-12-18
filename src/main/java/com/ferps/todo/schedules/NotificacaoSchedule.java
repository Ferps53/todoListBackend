package com.ferps.todo.schedules;

import com.ferps.todo.controller.NotificationController;
import com.ferps.todo.model.RegistroTokenNotificacao;
import com.ferps.todo.model.Tarefa;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class NotificacaoSchedule {

    @Inject
    NotificationController notificationController;

    final List<RegistroTokenNotificacao> registroTokenNotificacaoList = RegistroTokenNotificacao.findAll().list();

    @Scheduled(every = "10s")
    public void enviarNotificacoes() throws FirebaseMessagingException {
        Instant start = Instant.now();
        final List<Tarefa> listTarefas = gerarListaTarefas();
        final List<List<Tarefa>> listParticionada = particionarLista((int) Math.ceil((double)listTarefas.size()/4), listTarefas);

        List<Message> messageList = new ArrayList<>();

        //TODO: Fazer esse sistema usando threads

        messageList.addAll(messageBuilder(listParticionada.get(0)));
        messageList.addAll(messageBuilder(listParticionada.get(1)));
        messageList.addAll(messageBuilder(listParticionada.get(2)));
        messageList.addAll(messageBuilder(listParticionada.get(3)));

        BatchResponse response = notificationController.enviarNotificacao(messageList);

        System.out.println("Quantidade de sucesso: " + response.getSuccessCount());
        System.out.println("Quantidade de falhas: " + response.getFailureCount());


        Instant end = Instant.now();

        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
    }


    private List<Tarefa> gerarListaTarefas(){

        final LocalDateTime agora = LocalDateTime.now().plusMinutes(5);


        final List<String> listIdUsuarios = new ArrayList<>();

        for (RegistroTokenNotificacao registro : registroTokenNotificacaoList){
            listIdUsuarios.add(registro.getIdUsuario());
        }

        Map<String, Object> mapQuery = new HashMap<>();

        mapQuery.put("listIdUsuario", listIdUsuarios);
        mapQuery.put("agora", agora);

        return Tarefa.list("select t from Tarefa t where idUsuario in(:listIdUsuario) and dataExpiracao < :agora ", mapQuery);
    }

    private List<List<Tarefa>> particionarLista(int quantidadePorLista, List<Tarefa> tarefaList){
       return Partition.ofSize(tarefaList, quantidadePorLista);
    }

    private List<Message> messageBuilder(List<Tarefa> tarefas){
        List<Message> messageList = new ArrayList<>();

        for (Tarefa tarefa : tarefas){

            String fcmToken = null;
            String titulo = "Hora de Concluir a tarefa ':nomeTarefa'";
            titulo = titulo.replace(":nomeTarefa", tarefa.getTitulo());

            for (RegistroTokenNotificacao registro : registroTokenNotificacaoList){
                if(registro.getIdUsuario().equals(tarefa.getIdUsuario())){
                    fcmToken = registro.getFcmToken();
                }
            }

            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody("Lembre-se de deixar suas tarefas em dia!")
                            .build())
                    .setToken(fcmToken)
                    .build();
            messageList.add(message);

            messageList.add(message);
        }
        return messageList;
    }


}
