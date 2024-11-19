package com.meuProjeto.resource;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import com.meuProjeto.model.Cliente;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.repository.NoticiaRepository;
import com.meuProjeto.service.EmailScheduler;
import com.meuProjeto.service.EmailService;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

/*public class EmailSchedulerUnitTest {
    @Mock
    EmailService emailService;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    NoticiaRepository noticiaRepository;

    @InjectMocks
    EmailScheduler emailScheduler;

    Cliente cliente1;
    Cliente cliente2;
    Noticia noticia1;
    Noticia noticia2;

    @BeforeEach
    void setup() {
        cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setEmail("cliente1@example.com");
        cliente1.setNascimento(LocalDate.of(1990, 1, 1));

        cliente2 = new Cliente();
        cliente2.setNome("Cliente 2");
        cliente2.setEmail("cliente2@example.com");
        cliente2.setNascimento(LocalDate.of(1985, 2, 15));

        noticia1 = new Noticia();
        noticia1.setTitulo("Notícia 1");
        noticia1.setDescricao("Descrição 1");
        noticia1.setUrl("http://noticia1.com");
        noticia1.setProcessada(false);

        noticia2 = new Noticia();
        noticia2.setTitulo("Notícia 2");
        noticia2.setDescricao("Descrição 2");
        noticia2.setUrl("http://noticia2.com");
        noticia2.setProcessada(false);
    }

    @Test
    public void testEnviarEmailsDiarios_ComClientesENoticias() {
        // Mocka os retornos das dependências
        PanacheQuery<Noticia> mockQuery = mock(PanacheQuery.class);
        when(clienteRepository.findAllClientes()).thenReturn(Arrays.asList(cliente1, cliente2));
        when(noticiaRepository.find("processada = false")).thenReturn(mockQuery);
        
        //thenReturn(new MockList<>(Arrays.asList(noticia1, noticia2)));

        // Executa o método que será testado
        emailScheduler.enviarEmailsDiarios();

        // Verifica se o método de envio de e-mails foi chamado para cada cliente
        verify(emailService, times(1)).enviarEmailParaCliente(
                eq(cliente1.getNome()), eq(cliente1.getEmail()), eq(Arrays.asList(noticia1, noticia2)), eq(cliente1.getNascimento())
        );
        verify(emailService, times(1)).enviarEmailParaCliente(
                eq(cliente2.getNome()), eq(cliente2.getEmail()), eq(Arrays.asList(noticia1, noticia2)), eq(cliente2.getNascimento())
        );

        // Verifica se as notícias foram marcadas como processadas
        verify(noticiaRepository, times(1)).persist(anyList());
        verify(noticiaRepository, times(1)).flush();

        // Assegura que os objetos notícia foram atualizados
        assert noticia1.isProcessada(false);
        assert noticia2.isProcessada(false);
    }
    
}*/
