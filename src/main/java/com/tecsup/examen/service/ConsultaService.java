package com.tecsup.examen.service;

import com.tecsup.examen.entity.Company;
import com.tecsup.examen.entity.Consulta;
import com.tecsup.examen.enums.ResultadoConsulta;
import com.tecsup.examen.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarConsulta(String ruc,
                                  ResultadoConsulta resultado,
                                  String mensajeError,
                                  Integer providerStatusCode,
                                  Company company) {
        Consulta consulta = new Consulta();
        consulta.setRucConsultado(ruc);
        consulta.setResultado(resultado);
        consulta.setMensajeError(mensajeError);
        consulta.setProviderStatusCode(providerStatusCode);
        consulta.setCompany(company);
        consultaRepository.save(consulta);
    }
}