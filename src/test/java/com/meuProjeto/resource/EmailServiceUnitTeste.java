package com.meuProjeto.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.meuProjeto.model.Noticia;
import com.meuProjeto.service.EmailService;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

public class EmailServiceUnitTeste {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private Mailer mailer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnviarEmailParaCliente_ComAniversario() {
    
        String nomeCliente = "João";
        String emailCliente = "joao@gmail.com";
        LocalDate nascimento = LocalDate.now();
        List<Noticia> noticias = List.of(
            new Noticia("Notícia 1", "Descrição 1", "http://url1.com"),
            new Noticia("Notícia 2", "Descrição 2", null)
        );

       
        emailService.enviarEmailParaCliente(nomeCliente, emailCliente, noticias, nascimento);

        
        verify(mailer, times(1)).send(any(Mail.class));
    }

    @Test
    public void testEnviarEmailParaCliente_SemNoticias() {
        
        String nomeCliente = "João";
        String emailCliente = "joao@gmail.com";
        LocalDate nascimento = LocalDate.now();
        List<Noticia> noticias = List.of(); 

        
        emailService.enviarEmailParaCliente(nomeCliente, emailCliente, noticias, nascimento);

       
        verify(mailer, never()).send(any(Mail.class)); 
    }

    @Test
    public void testEnviarEmailParaCliente_ListaNula() {
      
        String nomeCliente = "João";
        String emailCliente = "joao@gmail.com";
        LocalDate nascimento = LocalDate.now();

        emailService.enviarEmailParaCliente(nomeCliente, emailCliente, null, nascimento);

       
        verify(mailer, never()).send(any(Mail.class));
    }
}
