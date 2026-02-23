package com.tecsup.examen.entity;

import com.tecsup.examen.enums.ResultadoConsulta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ruc_consultado", nullable = false, length = 11)
    private String rucConsultado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoConsulta resultado;

    @Column(name = "mensaje_error")
    private String mensajeError;

    @Column(name = "provider_status_code")
    private Integer providerStatusCode;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRucConsultado() { return rucConsultado; }
    public void setRucConsultado(String rucConsultado) { this.rucConsultado = rucConsultado; }

    public ResultadoConsulta getResultado() { return resultado; }
    public void setResultado(ResultadoConsulta resultado) { this.resultado = resultado; }

    public String getMensajeError() { return mensajeError; }
    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }

    public Integer getProviderStatusCode() { return providerStatusCode; }
    public void setProviderStatusCode(Integer providerStatusCode) { this.providerStatusCode = providerStatusCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}