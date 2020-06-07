package com.formacionbdi.microservicios.app.examenes.services;


import com.formacionbdi.microservicios.commons.examenes.bussines.AsignaturaBO;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.services.CommonService;

import java.util.List;

/**
 * @author beto
 */
public interface ExamenService extends CommonService<ExamenBO> {
    ExamenBO edit(Long id, ExamenBO alumno);
    List<ExamenBO> findByNombre(String term);
    Iterable<Asignatura> findAllAsignaturas();
    Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds);
}
