package com.formacionbdi.microservicios.commons.examenes.bussines;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ExamenBO {
    private Long id;

    @NotEmpty
    private String nombre;
    private Date createAt;

    List<PreguntaBO> preguntas;

    @NotNull
    AsignaturaBO asignatura;

    private boolean respondido;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<PreguntaBO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaBO> preguntas) {
        this.preguntas = preguntas;
    }

    public AsignaturaBO getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(AsignaturaBO asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }
}
