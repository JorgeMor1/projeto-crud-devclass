package com.meuProjeto.resource;



import java.util.List;

import com.meuProjeto.model.Cliente;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.service.ClienteService;
import com.meuProjeto.util.ErrorResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    ClienteRepository clienteRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarCliente(@Valid Cliente cliente) {
        
        try {
            clienteService.salvarCliente(cliente);
            return Response.status(Status.CREATED).entity(cliente).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Status.BAD_REQUEST)
                           .entity(new ErrorResponse("VALIDATION_ERROR", e.getMessage()))
                           .build();
        }catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST)
                           .entity(new ErrorResponse("VALIDATION_ERROR", e.getMessage()))
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro inesperado ao salvar cliente"))
                           .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> findAllClientes(
        @QueryParam("page") @DefaultValue("0") int page, 
        @QueryParam("size") @DefaultValue("10") int size,
        @QueryParam("sort") @DefaultValue("id") String sort) {
    return clienteRepository.findAll()
                            .page(page, size)
                            .list();
    }

       
}





