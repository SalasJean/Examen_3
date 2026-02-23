package com.tecsup.examen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.examen.exception.DecolectaErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private final DecolectaProperties properties;

    public FeignConfig(DecolectaProperties properties) {
        this.properties = properties;
    }

    // ─────────────────────────────────────────
    // Authorization: Bearer <TOKEN> en cada request
    // ─────────────────────────────────────────
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header("Authorization", "Bearer " + properties.getToken());
    }

    // ─────────────────────────────────────────
    // Registra el ErrorDecoder para interceptar
    // 4xx/5xx de Decolecta ANTES de que exploten
    // ─────────────────────────────────────────
    @Bean
    public ErrorDecoder errorDecoder() {
        return new DecolectaErrorDecoder(new ObjectMapper()); // ✅ ESTE ERA EL QUE FALTABA
    }
}