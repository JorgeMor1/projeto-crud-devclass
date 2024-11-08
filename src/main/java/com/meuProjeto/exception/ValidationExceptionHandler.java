package com.meuProjeto.exception;

import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.validation.ConstraintViolationException;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper <ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder errorMessage = new StringBuilder();
        exception.getConstraintViolations().forEach(violation -> 
            errorMessage.append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append("\n")
        );
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(errorMessage.toString())
                       .build();
    }
}
