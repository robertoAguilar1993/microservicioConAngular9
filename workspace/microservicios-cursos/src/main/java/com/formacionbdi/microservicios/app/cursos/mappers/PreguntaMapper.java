package com.formacionbdi.microservicios.app.cursos.mappers;

import com.formacionbdi.microservicios.commons.examenes.bussines.PreguntaBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PreguntaMapper {

    PreguntaMapper INSTANCE = Mappers.getMapper(PreguntaMapper.class);

    /**
     * @param preguntaBO
     * @return
     */
    Pregunta PreguntaToPreguntaBO(PreguntaBO preguntaBO);

    /**
     * @param pregunta
     * @return
     */
    PreguntaBO PreguntaBOToPregunta(Pregunta pregunta);

}
