package com.formacionbdi.microservicios.app.respuestas.api;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.services.RespuestaService;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RespuestaApi {

    private RespuestaService respuestaService;

    @Autowired
    public RespuestaApi(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> buscarAlumboPorIdExamenPorId(@PathVariable Long alumnoId, @PathVariable Long examenId){
        return ResponseEntity.ok(new ResponseResult<Iterable<Respuesta>>(respuestaService.findRespuestaByAlumnoByexamen(alumnoId,examenId)));
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerIdPorAlumno(@PathVariable Long alumnoId){
        return ResponseEntity.ok(new ResponseResult<Iterable<Long>>(respuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId)));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestaBOS){
        return ResponseEntity.ok(new ResponseResult<Iterable<Respuesta>>(respuestaService.saveAll(respuestaBOS)));
    }
}
