package com.formacionbdi.microservicios.app.examenes.mappers;

import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExamenMapper {
    ExamenMapper INSTANCE = Mappers.getMapper(ExamenMapper.class);

    /**
     * @param examenBO
     * @return
     */
    Examen ExamenToExamenBO(ExamenBO examenBO);

    /**
     * @param examen
     * @return
     */
    ExamenBO ExamenBOToExamen(Examen examen);

}
