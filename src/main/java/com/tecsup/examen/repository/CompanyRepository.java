package com.tecsup.examen.repository;

import com.tecsup.examen.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // Buscar por RUC exacto
    Optional<Company> findByRuc(String ruc);

    // Para el bonus: verificar si fue consultada en los últimos 10 minutos
    // Busca company por RUC cuyo createdAt sea mayor a X tiempo
    Optional<Company> findByRucAndCreatedAtAfter(String ruc, LocalDateTime after);
} //recuerda que este metodo nos permite evitar consultar a la api de manera recurrente y asi evitar el trafico
