package com.formacionbdi.microservicios.app.respuestas.clients;

import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {

    @GetMapping("/{id}")
    ResponseResult<Examen> obtenerxamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    ResponseResult<List<Long>> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntasIds);

}
