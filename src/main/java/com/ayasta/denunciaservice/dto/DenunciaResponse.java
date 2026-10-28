package com.ayasta.denunciaservice.dto;

import com.ayasta.denunciaservice.entity.Denuncia;

import java.time.LocalDateTime;

public class DenunciaResponse {

    private Integer id;
    private String dni;
    private LocalDateTime fecha;
    private String tipo;
    private String direccion;
    private String descripcion;
    private Integer estado;
    private String estadoTexto;

    public static DenunciaResponse desde(Denuncia denuncia) {
        DenunciaResponse response = new DenunciaResponse();
        response.setId(denuncia.getId());
        response.setDni(denuncia.getDni());
        response.setFecha(denuncia.getFecha());
        response.setTipo(denuncia.getTipo());
        response.setDireccion(denuncia.getDireccion());
        response.setDescripcion(denuncia.getDescripcion());
        response.setEstado(denuncia.getEstado());
        response.setEstadoTexto(
                denuncia.getEstado() != null && denuncia.getEstado() == Denuncia.ESTADO_ATENDIDA
                        ? "ATENDIDA"
                        : "PENDIENTE"
        );
        return response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getEstadoTexto() {
        return estadoTexto;
    }

    public void setEstadoTexto(String estadoTexto) {
        this.estadoTexto = estadoTexto;
    }
}
