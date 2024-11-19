package com.meuProjeto.resource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.meuProjeto.model.Cliente;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.repository.NoticiaRepository;
import com.meuProjeto.service.EmailScheduler;
import com.meuProjeto.service.EmailService;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public class EmailSchedulerUnitTest {
        @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private NoticiaRepository noticiaRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    void testEnviarEmailsDiarios() {
        // Arrange: Dados fictícios
        Cliente cliente1 = new Cliente("João", "joao@example.com", LocalDate.of(2000, 5, 10));
        Cliente cliente2 = new Cliente("Maria", "maria@example.com", LocalDate.of(1995, 7, 20));
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        Noticia noticia1 = new Noticia("Notícia 1", "Descrição 1", "https://testejorge.com");
        Noticia noticia2 = new Noticia("Notícia 2", "Descrição 2", "https://testejoorge.com");
        List<Noticia> noticias = Arrays.asList(noticia1, noticia2);

        PanacheQuery<Noticia> mockPanacheQuery = mock(PanacheQuery.class);
        when(mockPanacheQuery.list()).thenReturn(noticias);

        when(clienteRepository.listAll()).thenReturn(clientes);
        when(noticiaRepository.find("processada = false")).thenReturn(mockPanacheQuery);

       
        emailScheduler.enviarEmailsDiarios();

      
        verify(emailService, times(1)).enviarEmailParaCliente("João", "joao@example.com", noticias, LocalDate.of(2000, 5, 10));
        verify(emailService, times(1)).enviarEmailParaCliente("Maria", "maria@example.com", noticias, LocalDate.of(1995, 7, 20));
        
        assertTrue(noticia1.isProcessada());
        assertTrue(noticia2.isProcessada());

        verify(noticiaRepository, times(1)).persist(noticias);
        verify(noticiaRepository, times(1)).flush();
    }

}