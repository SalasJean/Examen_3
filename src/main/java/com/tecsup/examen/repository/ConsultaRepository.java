package com.tecsup.examen.repository;

import com.tecsup.examen.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Historial de consultas por RUC ordenado descendente por fecha
    // Cubre el endpoint GET /api/sunat/ruc/{ruc}/consultas
    List<Consulta> findByRucConsultadoOrderByCreatedAtDesc(String rucConsultado);
}