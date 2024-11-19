package com.meuProjeto.exception;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.stream.Collectors;

import com.meuProjeto.util.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper <ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        
        String mensagemErro = exception.getConstraintViolations().stream()
        .map(violation -> String.format("Campo '%s': %s", 
                violation.getPropertyPath(), 
                violation.getMessage()))
        .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", mensagemErro);

        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(errorResponse) 
                       .build();
    }
}
