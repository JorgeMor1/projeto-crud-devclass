package com.meuProjeto;
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
public class ClientesResource {

    @Inject
    ClientesRepository clientesRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarCliente(@Valid Clientes cliente) {
        try {
            // Verificar se o e-mail já existe
            if (clientesRepository.emailExistente(cliente.getEmail())) {
                throw new BadRequestException("O e-mail já está cadastrado.");
            }

            // Salvar cliente
            clientesRepository.salvar(cliente);
            return Response.status(Status.CREATED)
                           .entity(cliente)
                           .build();
        } catch (BadRequestException e) {
            // Retonrna erro 40 se o e-mail já está cadastrado
            return Response.status(Status.BAD_REQUEST)
                           .entity(new ErrorResponse("EMAIL_ALREADY_EXISTS", e.getMessage()))
                           .build();
        } catch (Exception e) {
            // Tratar alguns erros internos do sistema
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro inesperado ao salvar cliente"))
                           .build();
        }
    }


}

