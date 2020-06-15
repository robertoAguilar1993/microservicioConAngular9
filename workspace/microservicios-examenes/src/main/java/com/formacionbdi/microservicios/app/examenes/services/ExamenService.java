package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonService;

import java.util.List;

/**
 * @author beto
 */
public interface ExamenService extends CommonService<Examen> {
    Examen edit(Long id, Examen alumno);
    List<Examen> findByNombre(String term);
    Iterable<Asignatura> findAllAsignaturas();
    Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds);
}
