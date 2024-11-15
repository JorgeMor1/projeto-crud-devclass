package com.meuProjeto.resource;

import java.util.List;
import com.meuProjeto.model.Noticia;
import com.meuProjeto.repository.NoticiaRepository;
import com.meuProjeto.util.ErrorResponse;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/noticias")
public class NoticiaResource {
        
    @Inject
    NoticiaRepository noticiaRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response criarNoticia(@Valid Noticia noticia) {
        if (noticia == null) {
                return Response.status(Status.BAD_REQUEST)
                               .entity(new ErrorResponse("BAD_REQUEST", "A notícia não pode ser nula"))
                               .build();
        }

        try{
            noticiaRepository.salvar(noticia);
            return Response.status(Status.CREATED)
                           .entity(noticia)
                           .header("Location", "/noticias/" + noticia.getId())
                           .build();
            } catch (ConstraintViolationException e) {
                    return Response.status(Status.BAD_REQUEST)
                            .entity(new ErrorResponse("BAD_REQUEST", "Dados inválidos para a notícia"))
                            .build();
            } catch (PersistenceException e) {
                    return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro ao salvar a notícia no banco de dados"))
                            .build();
            } catch (Exception e) {
                   return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", "Erro inesperado ao salvar nova notícia"))
                           .build();
        }
    }

    @GET
    public List<Noticia> listarNoticiasNaoProcessada() {
        return noticiaRepository.getNoticiasNaoProcessadas();
        }


}

