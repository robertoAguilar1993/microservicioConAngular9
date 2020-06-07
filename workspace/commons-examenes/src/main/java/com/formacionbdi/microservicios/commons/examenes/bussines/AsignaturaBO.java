package com.formacionbdi.microservicios.commons.examenes.bussines;

import java.util.ArrayList;
import java.util.List;

/**
 * @author beto
 */
public class AsignaturaBO {
    private Long id;
    private String nombre;

    private AsignaturaBO padre;

    private List<AsignaturaBO> hijos;

    public AsignaturaBO() {
        this.hijos = new ArrayList<>();
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

    public AsignaturaBO getPadre() {
        return padre;
    }

    public void setPadre(AsignaturaBO padre) {
        this.padre = padre;
    }

    public List<AsignaturaBO> getHijos() {
        return hijos;
    }

    public void setHijos(List<AsignaturaBO> hijos) {
        this.hijos = hijos;
    }
}
