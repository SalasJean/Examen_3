package com.tecsup.examen.entity;

import com.tecsup.examen.enums.CondicionDomicilio;
import jakarta.persistence.*;
import com.tecsup.examen.enums.EstadoContribuyente;
import java.time.LocalDateTime;
import com.tecsup.examen.entity.Consulta;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    //definimos el id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //ahora las columnas con sus restricciones a las mas importantes

    @Column(unique = true, nullable = false, length = 11)
    private String ruc;

    @Column(name = "razon_social")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    private EstadoContribuyente estado;  // Se mapeará desde el String "ACTIVO" //devemos crear  esto en el enum recuerda

    @Enumerated(EnumType.STRING)
    private CondicionDomicilio condicion; // Se mapeará desde el String "HABIDO"

    private String direccion;
    private String ubigeo;
    private String distrito;
    private String provincia;
    private String departamento;

    @Column(name = "es_agente_retencion")
    private Boolean  esAgenteRetencion;

    
    @Column(name = "es_buen_contribuyente")
    private Boolean  esBuenContribuyente;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;//recuerda que para evitar errroes devemos nombrarlo explicitamente

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas = new ArrayList<>();
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public EstadoContribuyente getEstado() {
        return estado;
    }

    public void setEstado(EstadoContribuyente estado) {
        this.estado = estado;
    }

    public CondicionDomicilio getCondicion() {
        return condicion;
    }

    public void setCondicion(CondicionDomicilio condicion) {
        this.condicion = condicion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Boolean isEsAgenteRetencion() { return esAgenteRetencion; }
    public void setEsAgenteRetencion(Boolean esAgenteRetencion) { this.esAgenteRetencion = esAgenteRetencion; }

    public Boolean isEsBuenContribuyente() { return esBuenContribuyente; }
    public void setEsBuenContribuyente(Boolean esBuenContribuyente) { this.esBuenContribuyente = esBuenContribuyente; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}

