package com.formacionbdi.microservicios.app.cursos.mappers;

import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author beto
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AlumnoMapper {
    AlumnoMapper INSTANCE = Mappers.getMapper(AlumnoMapper.class);

    /**
     * @param alumnoBO
     * @return
     */
    Alumno AlumnoToAlumnoBO(AlumnoBO alumnoBO);


    /**
     * @param alumno
     * @return
     */
    AlumnoBO AlumnoBOToAlumno(Alumno alumno);

}
