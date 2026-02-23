package com.tecsup.examen.mapper;

import com.tecsup.examen.dto.response.CompanyResponse;
import com.tecsup.examen.dto.response.ConsultaResponse;
import com.tecsup.examen.dto.response.SunatRucResponse;
import com.tecsup.examen.entity.Company;
import com.tecsup.examen.entity.Consulta;
import com.tecsup.examen.enums.CondicionDomicilio;
import com.tecsup.examen.enums.EstadoContribuyente;
import org.springframework.stereotype.Component;

@Component
public class SunatMapper {

    public Company toEntity(SunatRucResponse response) {
        Company company = new Company();
        company.setRuc(response.numeroDocumento());
        company.setRazonSocial(response.razonSocial());
        company.setEstado(EstadoContribuyente.fromString(response.estado()));
        company.setCondicion(CondicionDomicilio.fromString(response.condicion()));
        company.setDireccion(response.direccion());
        company.setUbigeo(response.ubigeo());
        company.setDepartamento(response.departamento());
        company.setProvincia(response.provincia());
        company.setDistrito(response.distrito());
        company.setEsAgenteRetencion(response.esAgenteRetencion());
        company.setEsBuenContribuyente(response.esBuenContribuyente());
        return company;
    }

    public CompanyResponse toCompanyResponse(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getRuc(),
                company.getRazonSocial(),
                company.getEstado(),
                company.getCondicion(),
                company.getDireccion(),
                company.getUbigeo(),
                company.getDepartamento(),
                company.getProvincia(),
                company.getDistrito(),
                company.isEsAgenteRetencion(),
                company.isEsBuenContribuyente(),
                company.getCreatedAt()
        );
    }

    public ConsultaResponse toConsultaResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getRucConsultado(),
                consulta.getResultado(),
                consulta.getMensajeError(),
                consulta.getProviderStatusCode(),
                consulta.getCreatedAt()
        );
    }

    // Actualizar Company existente con datos frescos del proveedor

    public void updateEntity(Company company, SunatRucResponse response) {
        company.setRazonSocial(response.razonSocial());
        company.setEstado(EstadoContribuyente.fromString(response.estado()));
        company.setCondicion(CondicionDomicilio.fromString(response.condicion()));
        company.setDireccion(response.direccion());
        company.setUbigeo(response.ubigeo());
        company.setDepartamento(response.departamento());
        company.setProvincia(response.provincia());
        company.setDistrito(response.distrito());
        company.setEsAgenteRetencion(response.esAgenteRetencion());
        company.setEsBuenContribuyente(response.esBuenContribuyente());
    }
}