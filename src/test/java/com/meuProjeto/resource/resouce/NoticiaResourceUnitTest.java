package com.meuProjeto.resource.resouce;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.NoticiaRepository;
import com.meuProjeto.resource.NoticiaResource;
import com.meuProjeto.util.ErrorResponse;

public class NoticiaResourceUnitTest {

    @InjectMocks
    private NoticiaResource noticiaResource;

    @Mock
    private NoticiaRepository noticiaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /*   @Test
    public void testCriarNoticia_Success() {
        // Arrange
        Noticia noticia = new Noticia("Título", "Descrição", "https://example.com", false);
        when(noticiaRepository.salvar(noticia)).thenReturn(noticia);
        
        // Act
        Response response = noticiaResource.criarNoticia(noticia);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(noticia, response.getEntity());
    }*/

    @Test
    public void testCriarNoticia_ConstraintViolation() {
        // Arrange
        Noticia noticia = new Noticia(); // São os que causariam um erro de validação
        doThrow(ConstraintViolationException.class).when(noticiaRepository).salvar(noticia);

        // Act
        Response response = noticiaResource.criarNoticia(noticia);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Dados inválidos para a notícia", ((ErrorResponse) response.getEntity()).getMessage());
    }

    @Test
    public void testCriarNoticia_PersistenceException() {
        // Arrange
        Noticia noticia = new Noticia(); // Dados válidos para teste
        doThrow(PersistenceException.class).when(noticiaRepository).salvar(noticia);

        // Act
        Response response = noticiaResource.criarNoticia(noticia);

        // Assert
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Erro ao salvar a notícia no banco de dados", ((ErrorResponse) response.getEntity()).getMessage());
    }

    @Test
    public void testListarNoticiasNaoProcessada() {
        // Arrange
        List<Noticia> mockNoticias = List.of(new Noticia(), new Noticia());
        when(noticiaRepository.getNoticiasNaoProcessadas()).thenReturn(mockNoticias);

        // Act
        List<Noticia> result = noticiaResource.listarNoticiasNaoProcessada();

        // Assert
        assertEquals(mockNoticias.size(), result.size());
    }

    /* 
    @Test
    public void testCriarNoticia_ValidacaoCamposObrigatorios() {
        // Arrange
        Noticia noticia = new Noticia();
        noticia.setDescricao("Conteúdo válido.");
        
        doThrow(new ConstraintViolationException("Título é obrigatório", null)).when(noticiaRepository).salvar(noticia);
        
        // Act
        Response response = noticiaResource.criarNoticia(noticia);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Título é obrigatório", ((ErrorResponse) response.getEntity()).getMessage());
    }*/
}

