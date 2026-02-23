package com.tecsup.examen.dto.response;

import com.tecsup.examen.enums.CondicionDomicilio;
import com.tecsup.examen.enums.EstadoContribuyente;

import java.time.LocalDateTime;

public record CompanyResponse(
        Long id,
        String ruc,
        String razonSocial,
        EstadoContribuyente estado,
        CondicionDomicilio condicion,
        String direccion,
        String ubigeo,
        String departamento,
        String provincia,
        String distrito,
        Boolean  esAgenteRetencion,
        Boolean  esBuenContribuyente,
        LocalDateTime createdAt

) {}
