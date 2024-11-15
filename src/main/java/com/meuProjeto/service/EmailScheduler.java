package com.meuProjeto.service;

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

    // Método para enviar os e-mails todos os dias às 08:00 horas
    @Scheduled(cron = "0 0 8 * * ?")  
    @Transactional
    public void enviarEmailsDiarios() {
        List<Cliente> clientes = clienteRepository.listAll();
        List<Noticia> noticias = noticiaRepository.find("processada = false").list();
        
        for (Cliente cliente : clientes) {
            CompletableFuture.runAsync(() -> {
                emailService.enviarEmailParaCliente(cliente.getNome(), cliente.getEmail(), noticias, cliente.getNascimento());
            });
        }
        noticias.forEach(noticia -> noticia.setProcessada(true));
        noticiaRepository.persist(noticias);  
        noticiaRepository.flush();
    }
        
        
        
        
        
        
        // Verificar e enviar o e-mail para cada cliente
        //for (Cliente cliente : clientes) {
            //boolean isAniversario = cliente.getNascimento().equals(LocalDate.now());
            //boolean isAniversario = cliente.getNascimento() != null && cliente.getNascimento().equals(LocalDate.now());
            

        /*emailService.enviarEmailParaCliente(cliente.getNome(),cliente.getEmail(), noticias, isAniversario);
            emailService.enviarEmailParaCliente(cliente.getNome(), cliente.getEmail(), noticias, cliente.getNascimento());
            for (Noticia noticia : noticias) {
                noticia.setProcessada(true);  // Marca a notícia como processada
                noticiaRepository.persist(noticia);  // Persiste a notícia atualizada
            }
        
             //Forçando com  o flush para garantir que as mudanças sejam persistidas imediatamente
                noticiaRepository.flush();
        }*/
}




