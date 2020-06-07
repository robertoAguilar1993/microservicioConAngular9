package com.formacionbdi.microservicios.app.usuarios.api;

import com.formacionbdi.microservicios.app.usuarios.services.AlumnoService;

import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.GenericApi;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AlumnoApi extends GenericApi<AlumnoBO, AlumnoService> {

    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(new ResponseResult<Iterable<Alumno>>(service.findAllById(ids)));
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFotos(@PathVariable Long id){
        Resource imagen = service.verFoto(id);
        if(imagen == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody AlumnoBO alumnoBO, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return this.valid(result);
        }
        AlumnoBO alumnoDB = service.edit(id, alumnoBO);
        if(alumnoDB == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<AlumnoBO>(alumnoDB ));
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid  AlumnoBO alumnoBO, BindingResult result, @PathVariable Long id,
                                           @RequestParam MultipartFile archivo){
        if(result.hasErrors()){
            return this.valid(result);
        }

        try {
            if(!archivo.isEmpty()){
                alumnoBO.setFoto(archivo.getBytes());
            }
        }catch (Exception e){
            Map<String, Object> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseResult<Map>( null,errors));
        }
        AlumnoBO alumnoDB = service.editConFoto(id, alumnoBO);
        if(alumnoDB == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseResult<AlumnoBO>(alumnoDB ));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        List<AlumnoBO> alumnoBOList = service.findByNombreOrApellidos(term);
        if(alumnoBOList == null || alumnoBOList.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new ResponseResult<List<AlumnoBO>>(alumnoBOList));
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid AlumnoBO alumno, BindingResult result, @RequestParam MultipartFile archivo) {
        if(!archivo.isEmpty()){
            try {
                alumno.setFoto(archivo.getBytes());
            }catch (Exception e){
                Map<String, Object> errors = new HashMap<>();
                errors.put("error", e.getMessage());
                return ResponseEntity.badRequest().body(new ResponseResult<Map>( null,errors));
            }
        }
        return super.crear(alumno, result);
    }
}
