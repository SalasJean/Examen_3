package com.tecsup.examen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "decolecta")
public class DecolectaProperties {

    private String baseUrl;
    private String token;

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}