package com.formacionbdi.microservicios.app.usuarios.models.repository;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author beto
 */
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

    @Query("select a from Alumno a where upper(a.nombre) like upper(concat('%',?1,'%')) or upper(a.apellidos) like upper(concat('%',?1,'%'))")
    public List<Alumno> findByNombreOrApellidos(String term);

}
