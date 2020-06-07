package com.formacionbdi.microservicios.app.examenes.models.repository;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author beto
 */
public interface AsignaturaRepository extends PagingAndSortingRepository<Asignatura, Long> {

}
