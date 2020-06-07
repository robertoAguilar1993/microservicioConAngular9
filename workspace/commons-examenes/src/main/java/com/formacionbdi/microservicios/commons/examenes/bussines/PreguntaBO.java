package com.formacionbdi.microservicios.commons.examenes.bussines;

/**
 * @author beto
 */
public class PreguntaBO {
    private Long id;
    private String texto;

    private  ExamenBO examenBO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ExamenBO getExamenBO() {
        return examenBO;
    }

    public void setExamenBO(ExamenBO examenBO) {
        this.examenBO = examenBO;
    }
}
