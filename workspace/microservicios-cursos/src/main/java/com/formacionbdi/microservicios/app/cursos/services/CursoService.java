package com.formacionbdi.microservicios.app.cursos.services;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.services.CommonService;

import java.util.List;

public interface CursoService extends CommonService<Curso> {
    Curso edit(Long id, Curso alumno);
    Curso asignarAlumnos(Long id, List<AlumnoBO> alumnoBOS);
    Curso asignarExamenes(Long id, List<ExamenBO> examenes);
    Curso eliminarAlumnos(Long id, AlumnoBO alumnoBO);
    Curso eliminarExamen(Long id, ExamenBO examen);
    Curso findCursoByAlumnoId(Long id);
    void eliminarCursoAlumnoPorId(Long id);
}
