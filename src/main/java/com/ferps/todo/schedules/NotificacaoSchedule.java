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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class NotificacaoSchedule {

    @Inject
    NotificationController notificationController;

    static List<RegistroTokenNotificacao> registroTokenNotificacaoList = RegistroTokenNotificacao.findAll().list();
    static List<Message> messageList = new CopyOnWriteArrayList<>();

    @Scheduled(every = "5m")
    public void enviarNotificacoes() throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Instant start = Instant.now();

        registroTokenNotificacaoList = RegistroTokenNotificacao.listAll();
        messageList.clear();
        final List<Tarefa> listTarefas = gerarListaTarefas();

        System.out.println("List Registro:" +registroTokenNotificacaoList +"\n");
        System.out.println("List Tarefas:" + listTarefas+"\n");
        System.out.println("Enviando Notifs!");

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (Tarefa tarefa: listTarefas){
            var processo = executorService.submit(new MessageBuilder(tarefa));
            while(!processo.isDone()){
                processo.get();
            }
            if(executorService.isTerminated()){
                executorService.shutdown();
            }
        }

        if(!messageList.isEmpty()){
            Instant end = Instant.now();
            BatchResponse response = notificationController.enviarNotificacao(messageList);

            System.out.println("Quantidade de sucesso: " + response.getSuccessCount());
            System.out.println("Quantidade de falhas: " + response.getFailureCount());

            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
            System.out.println("-");
        }else {
            System.out.println("Não foram encontradas notificações");
        }
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

        return Tarefa.list("idUsuario in(:listIdUsuario) and dataExpiracao < :agora", mapQuery);
    }

    public static class MessageBuilder implements Runnable{

        public MessageBuilder(Tarefa tarefa){
            this.tarefa = tarefa;
        }

        Tarefa tarefa;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": está realizando build de tarefas");
            String fcmToken = null;
            String titulo = "Hora de Concluir a tarefa ':nomeTarefa'";
            titulo = titulo.replace(":nomeTarefa", tarefa.getTitulo());

            for (RegistroTokenNotificacao registro : registroTokenNotificacaoList){
                if(registro.getIdUsuario().equals(tarefa.getIdUsuario())){
                    fcmToken = registro.getFcmToken();
                    break;
                }
            }
            Message message =  Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody("Lembre-se de deixar suas tarefas em dia!")
                            .build())
                    .setToken(fcmToken)
                    .build();

            messageList.add(message);
            System.out.println(Thread.currentThread().getName() + ": CONCLUIU A TAREFA");
        }
    }

}


