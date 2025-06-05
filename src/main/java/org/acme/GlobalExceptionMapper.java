package org.acme;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;

            String mensagem = ex.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + " → " + v.getMessage())
                    .collect(Collectors.joining("; "));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("❌ Erros de validação: " + mensagem)
                    .build();
        }

        // Genérico para erros inesperados
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("⚠️ Erro interno do servidor: " + exception.getMessage())
                .build();
    }
}

