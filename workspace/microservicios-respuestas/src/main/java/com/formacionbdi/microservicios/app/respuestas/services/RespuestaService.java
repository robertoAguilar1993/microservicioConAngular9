package com.formacionbdi.microservicios.app.respuestas.services;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestaBOS);
    public Iterable<Respuesta> findRespuestaByAlumnoByexamen(Long alumnoId, Long examenId);
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
