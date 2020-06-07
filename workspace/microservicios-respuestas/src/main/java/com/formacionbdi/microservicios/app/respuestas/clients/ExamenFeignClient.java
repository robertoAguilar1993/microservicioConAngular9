package com.formacionbdi.microservicios.app.respuestas.clients;

import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {

    @GetMapping("/{id}")
    ResponseResult<ExamenBO> obtenerxamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    ResponseResult<List<Long>> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntasIds);

}
