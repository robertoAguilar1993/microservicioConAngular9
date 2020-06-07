package com.formacionbdi.microservicios.app.usuarios.services;

import com.formacionbdi.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacionbdi.microservicios.app.usuarios.mapper.AlumnoMapper;
import com.formacionbdi.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.ResponseSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author beto
 */
@Service("alumnoServiceImpl")
public class AlumnoServiceImpl implements AlumnoService{

    private AlumnoRepository alumnoRepository;
    private CursoFeignClient cursoFeignClient;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository, CursoFeignClient cursoFeignClient) {
        this.alumnoRepository = alumnoRepository;
        this.cursoFeignClient = cursoFeignClient;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<AlumnoBO> findAll() {
        Iterable<Alumno> alumnoIterable = alumnoRepository.findAll();
        if(alumnoIterable == null ){
            return null;
        }
        List<AlumnoBO> alumnoBOList = new ArrayList<>();
        alumnoIterable.forEach(alumno -> {
            alumnoBOList.add(AlumnoMapper.INSTANCE.AlumnoBOToAlumno(alumno));
        });
        return alumnoBOList;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlumnoBO> findById(Long id) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if(!optionalAlumno.isPresent()){
            return Optional.empty();
        }
        return Optional.of(AlumnoMapper.INSTANCE.AlumnoBOToAlumno(optionalAlumno.get()));
    }

    @Override
    @Transactional
    public AlumnoBO save(AlumnoBO alumno) {
        Alumno alumnoDB =  alumnoRepository.save(AlumnoMapper.INSTANCE.AlumnoToAlumnoBO(alumno));
        return AlumnoMapper.INSTANCE.AlumnoBOToAlumno(alumnoDB);
    }

    @Override
    public AlumnoBO edit(Long id, AlumnoBO alumno) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if(!optionalAlumno.isPresent()){
            return null;
        }
        Alumno alumnoDB = optionalAlumno.get();
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellidos(alumno.getApellidos());
        alumnoDB.setEmail(alumno.getEmail());

        return AlumnoMapper.INSTANCE.AlumnoBOToAlumno( alumnoRepository.save(alumnoDB));
    }

    @Override
    public AlumnoBO editConFoto(Long id, AlumnoBO alumno) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if(!optionalAlumno.isPresent()){
            return null;
        }
        Alumno alumnoDB = optionalAlumno.get();
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellidos(alumno.getApellidos());
        alumnoDB.setEmail(alumno.getEmail());
        alumnoDB.setFoto(alumno.getFoto());
        return AlumnoMapper.INSTANCE.AlumnoBOToAlumno( alumnoRepository.save(alumnoDB));
    }

    @Override
    public Resource verFoto(Long id) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        if(!optionalAlumno.isPresent() || optionalAlumno.get().getFoto() == null){
            return null;
        }
        return new ByteArrayResource(optionalAlumno.get().getFoto());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoBO> findByNombreOrApellidos(String term) {

        Iterable<Alumno> alumnoIterable = this.alumnoRepository.findByNombreOrApellidos(term);
        if(alumnoIterable == null ){
            return null;
        }
        List<AlumnoBO> alumnoBOList = new ArrayList<>();
        alumnoIterable.forEach(alumno -> {
            alumnoBOList.add(AlumnoMapper.INSTANCE.AlumnoBOToAlumno(alumno));
        });
        return alumnoBOList;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return this.alumnoRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        alumnoRepository.deleteById(id);
        this.cursoFeignClient.eliminarCursoAlumnoPorId(id);
    }

    @Override
    public ResponseSearchResult<List<AlumnoBO>> findAll(Pageable pageable) {
        Page<Alumno> alumnoPage = alumnoRepository.findAll(pageable);
        if(alumnoPage == null ){
            return null;
        }
        List<AlumnoBO> alumnoBOList = new ArrayList<>();
        alumnoPage.get().forEach(alumno -> {
            alumnoBOList.add(AlumnoMapper.INSTANCE.AlumnoBOToAlumno(alumno));
        });

        return new ResponseSearchResult<List<AlumnoBO>>(alumnoBOList, alumnoPage.getTotalPages(), alumnoPage.getTotalElements());
    }
}
