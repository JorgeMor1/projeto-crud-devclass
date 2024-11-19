package com.meuProjeto.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.NoticiaRepository;
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

   @Test
    public void testCriarNoticia_Success() {
        Noticia novaNoticia = new Noticia("Título de Exemplo", "Descrição válida", "https://valid-url.com");
    
    when(noticiaRepository.salvar(any(Noticia.class))).thenAnswer(invocation -> {
        Noticia noticia = invocation.getArgument(0);
        Field idField = Noticia.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(noticia, 1L);; 
        return noticia;
    });

    Response response = noticiaResource.criarNoticia(novaNoticia);

   
    assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(), "Código de status deveria ser 201 (Created)");
    assertNotNull(response.getEntity(), "Entidade retornada não deveria ser nula");
    assertTrue(response.getHeaders().containsKey("Location"), "Cabeçalho Location deveria estar presente");
    assertEquals("/noticias/1", response.getHeaderString("Location"), "O cabeçalho Location está incorreto");

    Noticia noticiaCriada = (Noticia) response.getEntity();
    assertEquals("Título de Exemplo", noticiaCriada.getTitulo());
    assertEquals("Descrição válida", noticiaCriada.getDescricao());
    assertEquals("https://valid-url.com", noticiaCriada.getUrl());
    }

    @Test
    public void testCriarNoticia_ConstraintViolation() {
       
        Noticia noticia = new Noticia(); 
        doThrow(ConstraintViolationException.class).when(noticiaRepository).salvar(noticia);
        
        Response response = noticiaResource.criarNoticia(noticia);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Dados inválidos para a notícia", ((ErrorResponse) response.getEntity()).getMessage());
    }

    @Test
    public void testCriarNoticia_PersistenceException() {
        Noticia noticia = new Noticia();
        doThrow(PersistenceException.class).when(noticiaRepository).salvar(noticia);

        Response response = noticiaResource.criarNoticia(noticia);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Erro ao salvar a notícia no banco de dados", ((ErrorResponse) response.getEntity()).getMessage());
    }

    @Test
    public void testListarNoticiasNaoProcessada() {
       
        List<Noticia> mockNoticias = List.of(new Noticia(), new Noticia());
        when(noticiaRepository.getNoticiasNaoProcessadas(0, 0, null)).thenReturn(mockNoticias);

      
        List<Noticia> result = noticiaResource.listarNoticiasNaoProcessada(0,0,null);

        assertEquals(mockNoticias.size(), result.size());
    }

    
    @Test
    public void testCriarNoticia_ValidacaoCamposObrigatorios() {
        
        Noticia noticia = new Noticia();
        noticia.setDescricao("Conteúdo válido.");
        
        doThrow(new ConstraintViolationException("Título é obrigatório", null)).when(noticiaRepository).salvar(noticia);
        
        
        Response response = noticiaResource.criarNoticia(noticia);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Dados inválidos para a notícia", ((ErrorResponse) response.getEntity()).getMessage());
    }
}

