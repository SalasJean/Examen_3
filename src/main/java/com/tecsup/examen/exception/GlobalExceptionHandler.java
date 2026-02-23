package com.tecsup.examen.exception;

import com.tecsup.examen.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────
    // RUC con formato inválido
    // ─────────────────────────────────────────
    @ExceptionHandler(RucValidationException.class)
    public ResponseEntity<ErrorResponse> handleRucValidation(RucValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    // ─────────────────────────────────────────
    // Error del proveedor Decolecta (4xx / 5xx)
    // Devuelve el mismo status code que mandó el proveedor
    // ─────────────────────────────────────────
    @ExceptionHandler(ProviderException.class)
    public ResponseEntity<ErrorResponse> handleProvider(ProviderException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        if (status == null) status = HttpStatus.BAD_GATEWAY;
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(ex.getMessage()));
    }

    // ─────────────────────────────────────────
    // Cualquier otro error no controlado
    // Nunca exponemos el stacktrace al cliente
    // ─────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor. Intente más tarde."));
    }
}