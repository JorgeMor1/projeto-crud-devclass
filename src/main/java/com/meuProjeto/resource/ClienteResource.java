package com.meuProjeto.resource;
import com.meuProjeto.model.Cliente;
import com.meuProjeto.repository.ClienteRepository;
import com.meuProjeto.util.ErrorResponse;

//import java.util.List;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
public class ClienteResource {

    @Inject
    ClienteRepository clienteRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarCliente(@Valid Cliente cliente) {
        try {
            if (clienteRepository.emailExistente(cliente.getEmail())) {
                throw new BadRequestException("O e-mail já está cadastrado.");
            }

            clienteRepository.salvar(cliente);
            return Response.status(Status.CREATED)
                           .entity(cliente)
                           .build();
        } catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST)
                           .entity(new ErrorResponse("EMAIL_ALREADY_EXISTS", e.getMessage()))
                           .build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro inesperado ao salvar cliente"))
                           .build();
        }
    }


}

