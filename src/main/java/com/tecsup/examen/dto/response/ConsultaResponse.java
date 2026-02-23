package com.tecsup.examen.dto.response;

import com.tecsup.examen.enums.ResultadoConsulta;
import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        String rucConsultado,
        ResultadoConsulta resultado,
        String mensajeError,
        Integer providerStatusCode,
        LocalDateTime createdAt
) {}