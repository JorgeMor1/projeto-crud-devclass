package com.meuProjeto;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
//import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/noticias")
public class NoticiasResource {

    @Inject
    NoticiasRepository noticiasRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarNoticia(@Valid Noticias noticia) {
       
        try{
            // Salvar notícia
            noticiasRepository.salvar(noticia);
            return Response.status(Status.CREATED)
                           .entity(noticia)
                           .header("Location", "/noticias/" + noticia.getId())
                           .build();
            } catch (ConstraintViolationException e) {
                    // Erro de validação de dados
                    return Response.status(Status.BAD_REQUEST)
                            .entity(new ErrorResponse("BAD_REQUEST", "Dados inválidos para a notícia"))
                            .build();
                
            } catch (PersistenceException e) {
                    // Erro de persistência, como violação de chave única
                    return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro ao salvar a notícia no banco de dados"))
                            .build();
        
        } catch (Exception e) {
            // Tratar alguns erros internos do sistema
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro inesperado ao salvar nova notícia"))
                           .build();
        }
    }


}

