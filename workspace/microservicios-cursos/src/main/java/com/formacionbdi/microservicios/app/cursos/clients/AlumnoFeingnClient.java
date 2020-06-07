package com.formacionbdi.microservicios.app.cursos.clients;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeingnClient {

    @GetMapping("/alumnos-por-curso")
    ResponseResult<Iterable<Alumno>> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
