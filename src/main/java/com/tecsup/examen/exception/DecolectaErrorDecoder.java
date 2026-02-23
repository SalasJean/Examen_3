package com.tecsup.examen.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.examen.dto.response.ProviderErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class DecolectaErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public DecolectaErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String mensajeProveedor = extraerMensaje(response);

        return switch (status) {
            case 400, 422 -> new ProviderException( // ✅ agregamos 422
                    mensajeProveedor != null ? mensajeProveedor : "Solicitud inválida al proveedor",
                    status
            );
            case 401 -> new ProviderException(
                    "Token inválido o expirado. Verifica DECOLECTA_TOKEN",
                    status
            );
            case 404 -> new ProviderException(
                    mensajeProveedor != null ? mensajeProveedor : "RUC no encontrado en el proveedor",
                    status
            );
            case 500, 502, 503, 504 -> new ProviderException(
                    "El proveedor SUNAT no está disponible en este momento (status: " + status + ")",
                    status
            );
            default -> new ProviderException(
                    mensajeProveedor != null ? mensajeProveedor : "Error inesperado del proveedor (status: " + status + ")",
                    status
            );
        };
    }

    // ─────────────────────────────────────────
    // Extrae el campo "message" del body del proveedor
    // si no puede, retorna null sin explotar
    // ─────────────────────────────────────────
    private String extraerMensaje(Response response) {
        if (response.body() == null) return null;
        try (InputStream body = response.body().asInputStream()) {
            ProviderErrorResponse error = objectMapper.readValue(body, ProviderErrorResponse.class);
            return error.message();
        } catch (IOException e) {
            return null;
        }
    }

    //recuerda que aui nos estamos apoyando en el uso de jackson ahora si no queremos usar jackson tmabien
    //podemos usar si el uso de este recuerda tomar en cuenta eso si?

}
