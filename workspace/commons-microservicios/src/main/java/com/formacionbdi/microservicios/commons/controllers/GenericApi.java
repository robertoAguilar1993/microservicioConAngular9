package com.formacionbdi.microservicios.commons.controllers;


import com.formacionbdi.microservicios.commons.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class GenericApi<E, S extends CommonService<E>>{

    @Autowired
    protected S service;


    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(new ResponseResult<Iterable<E>>(service.findAll()));
    }

    @GetMapping("/pagina")
    public ResponseEntity<?> pagina(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<E> entityOptional = service.findById(id);
        return ResponseEntity.ok().body(new ResponseResult<E>(entityOptional.get()));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result){
        if(result.hasErrors()){
            return this.valid(result);
        }
       return  ResponseEntity.status(HttpStatus.CREATED).body( new ResponseResult<E>(service.save(entity)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?>  valid(BindingResult result){
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "La campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ResponseResult<E>( null,errors));
    }


}
