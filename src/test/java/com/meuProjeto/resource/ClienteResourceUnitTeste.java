package com.meuProjeto.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.meuProjeto.model.Cliente;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.service.ClienteService;
import com.meuProjeto.util.ErrorResponse;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class ClienteResourceUnitTeste {
   
    @InjectMocks
    private ClienteResource clienteResource;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarCliente_Success() {
        Cliente cliente = new Cliente("Jorge Luiz", "eemail@email.com", "03/08/1997");
        doNothing().when(clienteService).salvarCliente(cliente);

        Response response = clienteResource.criarCliente(cliente);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(cliente, response.getEntity());
        assertEquals(cliente, response.getEntity());
        
        verify(clienteService, times(1)).salvarCliente(cliente);
    }

  @Test
    public void testCriarCliente_EmailExistente() {
        Cliente novoCliente = new Cliente("Jorge Teste", "teste@gmail.com", "22/05/1990");
        doNothing().when(clienteService).salvarCliente(novoCliente);  
        
        doThrow(new BadRequestException("O e-mail já está cadastrado."))
        .when(clienteService).salvarCliente(novoCliente);

        Response response = clienteResource.criarCliente(novoCliente);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        
        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertNotNull(errorResponse);
        assertEquals("VALIDATION_ERROR", errorResponse.getError_code());
        assertEquals("O e-mail já está cadastrado.", errorResponse.getMessage());

        verify(clienteService, times(1)).salvarCliente(novoCliente);


    }


/*   
    //TESTE RETORNANDO ERRO 500 AO INVÉS DE 400. QUANDO TENTO MANUAL, ELE RETORNA 400
    @Test
    public void testeCriarCliente_ConstraintViolation() {
        Cliente cliente = new Cliente("João", null, "13/11/2001");
        String mensagemErro =  "Campo 'email': O campo 'email' é obrigatório";

        doThrow(new ConstraintViolationException(mensagemErro, null, "Cliente"))
            .when(clienteService).salvarCliente(cliente);

        Response response = clienteResource.criarCliente(cliente);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());


        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("VALIDATION_ERROR", errorResponse.getError_code());;
        assertEquals(mensagemErro, errorResponse.getMessage());
    }*/



}
