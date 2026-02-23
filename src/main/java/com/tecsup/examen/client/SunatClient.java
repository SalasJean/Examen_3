package com.tecsup.examen.client;

import com.tecsup.examen.config.FeignConfig;
import com.tecsup.examen.dto.response.SunatRucResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "decolecta-client",
        url = "${decolecta.base-url}",
        configuration = FeignConfig.class
)
public interface SunatClient {

    // GET https://api.decolecta.com/v1/sunat/ruc?numero=20601030013
    @GetMapping("/sunat/ruc")
    SunatRucResponse consultarRuc(@RequestParam("numero") String numero);
}