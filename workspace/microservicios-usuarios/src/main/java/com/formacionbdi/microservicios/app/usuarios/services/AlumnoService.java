package com.formacionbdi.microservicios.app.usuarios.services;

import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author beto
 */
public interface AlumnoService extends CommonService<AlumnoBO> {
    AlumnoBO edit(Long id, AlumnoBO alumno);
    AlumnoBO editConFoto(Long id, AlumnoBO alumno);
    Resource verFoto(Long id);
    List<AlumnoBO> findByNombreOrApellidos(String term);
    Iterable<Alumno> findAllById(Iterable<Long> ids);
}
