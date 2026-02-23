package com.tecsup.examen.service;

import com.tecsup.examen.client.SunatClient;
import com.tecsup.examen.dto.response.CompanyResponse;
import com.tecsup.examen.dto.response.ConsultaResponse;
import com.tecsup.examen.dto.response.SunatRucResponse;
import com.tecsup.examen.entity.Company;
import com.tecsup.examen.enums.ResultadoConsulta;
import com.tecsup.examen.exception.ProviderException;
import com.tecsup.examen.exception.RucValidationException;
import com.tecsup.examen.mapper.SunatMapper;
import com.tecsup.examen.repository.CompanyRepository;
import com.tecsup.examen.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class SunatService {

    private final SunatClient sunatClient;
    private final CompanyRepository companyRepository;
    private final ConsultaRepository consultaRepository;
    private final SunatMapper sunatMapper;
    private final ConsultaService consultaService; // ✅ NUEVO

    public SunatService(SunatClient sunatClient,
                        CompanyRepository companyRepository,
                        ConsultaRepository consultaRepository,
                        SunatMapper sunatMapper,
                        ConsultaService consultaService) { // ✅ NUEVO
        this.sunatClient = sunatClient;
        this.companyRepository = companyRepository;
        this.consultaRepository = consultaRepository;
        this.sunatMapper = sunatMapper;
        this.consultaService = consultaService; // ✅ NUEVO
    }

    @Transactional
    public CompanyResponse consultarRuc(String ruc) {

        validarRuc(ruc);

        Optional<Company> cachedCompany = companyRepository
                .findByRucAndCreatedAtAfter(ruc, LocalDateTime.now().minusMinutes(10));

        if (cachedCompany.isPresent()) {
            // ✅ usa consultaService en lugar de método privado
            consultaService.registrarConsulta(ruc, ResultadoConsulta.SUCCESS, null, null, cachedCompany.get());
            return sunatMapper.toCompanyResponse(cachedCompany.get());
        }

        try {
            SunatRucResponse response = sunatClient.consultarRuc(ruc);
            Company company = guardarOActualizarCompany(ruc, response);
            // ✅ usa consultaService
            consultaService.registrarConsulta(ruc, ResultadoConsulta.SUCCESS, null, null, company);
            return sunatMapper.toCompanyResponse(company);

        } catch (ProviderException ex) {
            // ✅ REQUIRES_NEW garantiza que esto se guarda aunque haya rollback
            consultaService.registrarConsulta(ruc, ResultadoConsulta.ERROR, ex.getMessage(), ex.getStatusCode(), null);
            throw ex;

        } catch (Exception ex) {
            consultaService.registrarConsulta(ruc, ResultadoConsulta.ERROR, "Error inesperado: " + ex.getMessage(), 500, null);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> obtenerHistorial(String ruc) {
        validarRuc(ruc);
        return consultaRepository
                .findByRucConsultadoOrderByCreatedAtDesc(ruc)
                .stream()
                .map(sunatMapper::toConsultaResponse)
                .toList();
    }

    private void validarRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{11}")) {
            throw new RucValidationException("RUC debe tener exactamente 11 dígitos");
        }
    }

    private Company guardarOActualizarCompany(String ruc, SunatRucResponse response) {
        Optional<Company> existing = companyRepository.findByRuc(ruc);
        if (existing.isPresent()) {
            Company company = existing.get();
            sunatMapper.updateEntity(company, response);
            return companyRepository.saveAndFlush(company);
        } else {
            Company company = sunatMapper.toEntity(response);
            return companyRepository.saveAndFlush(company);
        }
    }
}