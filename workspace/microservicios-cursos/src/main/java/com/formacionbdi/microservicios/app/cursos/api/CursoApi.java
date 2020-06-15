package com.formacionbdi.microservicios.app.cursos.api;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.controllers.GenericApi;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CursoApi extends GenericApi<Curso, CursoService> {

    /*@GetMapping("/listar-baleanceador-test")
    public ResponseEntity<?> listar() {
        Iterable<Curso> cursos = service.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("data", cursos);
        map.put("puerto",name);
        return ResponseEntity.ok().body(new ResponseResult<Map>(map));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso cursoBO, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.valid(result);
        }
        Curso cursoDB = service.edit(id, cursoBO);
        if(cursoDB == null){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<Curso>(cursoDB));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<AlumnoBO> alumnos, @PathVariable Long id){
        Curso o = this.service.asignarAlumnos(id,alumnos);
        if(o == null){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<Curso>(o));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumnos(@RequestBody AlumnoBO alumno, @PathVariable Long id){
        Curso o = this.service.eliminarAlumnos(id,alumno);
        if(o == null){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<Curso>(o));
    }
    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
        Curso cursoBO = service.findCursoByAlumnoId(id);
        return ResponseEntity.ok().body(new ResponseResult<Curso>(cursoBO));
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id){
        Curso o = this.service.asignarExamenes(id,examenes);
        if(o == null){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<Curso>(o));
    }
    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarAlumnos(@RequestBody Examen examen, @PathVariable Long id){
        Curso o = this.service.eliminarExamen(id,examen);
        if(o == null){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<Curso>(o));
    }

    @DeleteMapping("/eliminar-alumno/{id}")
    public  ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
