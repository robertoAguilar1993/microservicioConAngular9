package com.formacionbdi.microservicios.app.examenes.api;

import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.GenericApi;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExamenApi extends GenericApi<ExamenBO, ExamenService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody ExamenBO examenBO, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.valid(result);
        }
        ExamenBO examenDB = service.edit(id, examenBO);
        if(examenDB == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<ExamenBO>(examenDB));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(new ResponseResult<List<ExamenBO>>(service.findByNombre(term)));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(new ResponseResult<Iterable<Asignatura>>(service.findAllAsignaturas()));
    }

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntasIds){
        return ResponseEntity.ok(new ResponseResult<Iterable<Long>>(service.findExamenesIdsConRespuestasByPreguntaIds(preguntasIds)));
    }
}
