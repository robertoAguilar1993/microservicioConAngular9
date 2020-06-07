package com.formacionbdi.microservicios.app.cursos.models.repository;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.models.entity.CursoAlumno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoAlumnoRepository extends PagingAndSortingRepository<CursoAlumno, Long> {
    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId = ?1")
    public void eliminarCursoAlumnoPorId(Long id);
}
