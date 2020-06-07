package com.formacionbdi.microservicios.app.examenes.mappers;

import com.formacionbdi.microservicios.commons.examenes.bussines.AsignaturaBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AsignaturaMappers {
    AsignaturaMappers INSTANCE = Mappers.getMapper(AsignaturaMappers.class);

    /**
     * @param asignaturaBO
     * @return
     */
    Asignatura AsignaturaToAsignaturaBO(AsignaturaBO asignaturaBO);

    /**
     * @param asignatura
     * @return
     */
    AsignaturaBO AsignaturaBOToAsignatura(Asignatura asignatura);

}
