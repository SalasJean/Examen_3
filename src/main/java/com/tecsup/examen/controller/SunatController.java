package com.tecsup.examen.controller;

import com.tecsup.examen.dto.response.CompanyResponse;
import com.tecsup.examen.dto.response.ConsultaResponse;
import com.tecsup.examen.service.SunatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sunat")
public class SunatController {

    private final SunatService sunatService;

    // Inyección por constructor
    public SunatController(SunatService sunatService) {
        this.sunatService = sunatService;
    }

    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<CompanyResponse> consultarRuc(@PathVariable String ruc) {
        CompanyResponse response = sunatService.consultarRuc(ruc);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ruc/{ruc}/consultas")
    public ResponseEntity<List<ConsultaResponse>> obtenerHistorial(@PathVariable String ruc) {
        List<ConsultaResponse> historial = sunatService.obtenerHistorial(ruc);
        return ResponseEntity.ok(historial);
    }
}
