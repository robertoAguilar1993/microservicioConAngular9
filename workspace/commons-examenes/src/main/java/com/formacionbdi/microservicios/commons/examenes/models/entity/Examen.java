package com.formacionbdi.microservicios.commons.examenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author beto
 */
@Entity
@Table(name = "examenes")
public class Examen {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;

    @JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaPadre;

    @JsonIgnoreProperties(value = { "handler","hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaHija;

    @Transient
    private boolean respondido;

    /**
     * not args
     */
    public Examen() {
        this.preguntas = new ArrayList<>();
    }

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

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

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();
        preguntas.forEach(this::addPreguntas);
    }

    public void addPreguntas(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.setExamen(this);
    }

    public void removePreguntas(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.setExamen(null);
    }


    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }

    public Asignatura getAsignaturaPadre() {
        return asignaturaPadre;
    }

    public void setAsignaturaPadre(Asignatura asignaturaPadre) {
        this.asignaturaPadre = asignaturaPadre;
    }

    public Asignatura getAsignaturaHija() {
        return asignaturaHija;
    }

    public void setAsignaturaHija(Asignatura asignaturaHija) {
        this.asignaturaHija = asignaturaHija;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Examen)){
            return false;
        }

        Examen e= (Examen) obj;

        return this.id != null && this.id.equals(e.id);
    }
}
