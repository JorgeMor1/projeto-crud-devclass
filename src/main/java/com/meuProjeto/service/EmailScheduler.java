package com.meuProjeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.meuProjeto.model.Cliente;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.repository.NoticiaRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmailScheduler {

    @Inject
    EmailService emailService;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    NoticiaRepository noticiaRepository;


    // Método vai enviar os e-mails todos os dias às 08:00 horas
    @Scheduled(cron = "0 0 8 * * ?")  
    @Transactional
    public void enviarEmailsDiarios() {
        List<Cliente> clientes = clienteRepository.listAll();
        List<Noticia> noticias = noticiaRepository.find("processada = false").list();
        
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Cliente cliente : clientes) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                emailService.enviarEmailParaCliente(cliente.getNome(), cliente.getEmail(), noticias, cliente.getNascimento());
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        noticias.forEach(noticia -> noticia.setProcessada(true));
        noticiaRepository.persist(noticias);  
        noticiaRepository.flush();
    }     

}




