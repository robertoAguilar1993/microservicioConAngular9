package com.formacionbdi.microservicios.app.cursos.clients;

import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface ResponseFeignClient {

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    ResponseResult<Iterable<Long>> obtenerIdPorAlumno(@PathVariable Long alumnoId);
}
