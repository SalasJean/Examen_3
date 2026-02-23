package com.tecsup.examen.repository;

import com.tecsup.examen.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByRucConsultadoOrderByCreatedAtDesc(String rucConsultado);
}