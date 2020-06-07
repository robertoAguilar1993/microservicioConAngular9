package com.formacionbdi.microservicios.app.cursos.services;

import com.formacionbdi.microservicios.app.cursos.clients.AlumnoFeingnClient;
import com.formacionbdi.microservicios.app.cursos.clients.ResponseFeignClient;
import com.formacionbdi.microservicios.app.cursos.mappers.ExamenMapper;
import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.models.entity.CursoAlumno;
import com.formacionbdi.microservicios.app.cursos.models.repository.CursoAlumnoRepository;
import com.formacionbdi.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacionbdi.microservicios.commons.alumnos.bussines.AlumnoBO;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.controllers.ResponseSearchResult;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beto
 */
@Service("cursoServiceImpl")
public class CursoServiceImpl implements CursoService{

    private CursoRepository cursoRepository;
    private ResponseFeignClient responseFeignClient;
    private AlumnoFeingnClient alumnoFeingnClient;
    private CursoAlumnoRepository cursoAlumnoRepository;

    @Autowired
    public CursoServiceImpl(CursoRepository cursoRepository, ResponseFeignClient responseFeignClient, AlumnoFeingnClient alumnoFeingnClient, CursoAlumnoRepository cursoAlumnoRepository) {
        this.cursoRepository = cursoRepository;
        this.responseFeignClient = responseFeignClient;
        this.alumnoFeingnClient = alumnoFeingnClient;
        this.cursoAlumnoRepository = cursoAlumnoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Curso> findAll() {
        List<Curso> cursos= (List<Curso>) this.cursoRepository.findAll();
        if(cursos != null && !cursos.isEmpty()){
            return cursos.stream().map(c->{
                c.getCursoAlumnos().forEach(ca->{
                    Alumno alumno = new Alumno();
                    alumno.setId(ca.getAlumnoId());
                    c.addAlumnos(alumno);
                });
                return c;
            }).collect(Collectors.toList());
        }
        return cursos;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        Optional<Curso> cursoOptional =  this.cursoRepository.findById(id);
        if(!cursoOptional.isPresent()){
            return Optional.empty();
        }
        Curso curso = cursoOptional.get();
        if(curso != null && !curso.getCursoAlumnos().isEmpty()){
            List<Long> ids = curso.getCursoAlumnos().stream().map(ca->ca.getAlumnoId()).collect(Collectors.toList());
            ResponseResult<Iterable<Alumno>>  responseResult= this.alumnoFeingnClient.obtenerAlumnosPorCurso(ids);
            if(responseResult != null && responseResult.getOperationStatus()){
                curso.setAlumnos((List<Alumno>) responseResult.getResult());
            }
        }
        return this.cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return   this.cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public Curso edit(Long id, Curso cursoBO) {
        Optional<Curso> optionalCurso = this.cursoRepository.findById(id);
        if(!optionalCurso.isPresent()){
            return null;
        }
        Curso cursoDB = optionalCurso.get();
        cursoDB.setNombre(cursoBO.getNombre());

        return  this.cursoRepository.save(cursoDB);
    }

    @Override
    @Transactional
    public Curso asignarAlumnos(Long id, List<AlumnoBO> alumnoBOS) {
        Optional<Curso> optionalCurso = this.cursoRepository.findById(id);
        if(!optionalCurso.isPresent()){
            return null;
        }
        Curso cursoDB = optionalCurso.get();
        alumnoBOS.forEach(alumnoBO -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumnoBO.getId());
            cursoAlumno.setCurso(cursoDB);
            cursoDB.addCursoAlumnos(cursoAlumno);
        });

        return this.cursoRepository.save(cursoDB);
    }

    @Override
    public Curso asignarExamenes(Long id, List<ExamenBO> examenes) {
        Optional<Curso> optionalCurso = this.cursoRepository.findById(id);
        if(!optionalCurso.isPresent()){
            return null;
        }
        Curso cursoDB = optionalCurso.get();
        examenes.forEach(e -> cursoDB.addExamen(ExamenMapper.INSTANCE.ExamenToExamenBO(e)));
        return this.cursoRepository.save(cursoDB);
    }

    @Override
    @Transactional
    public Curso eliminarAlumnos(Long id, AlumnoBO alumnoBO) {
        Optional<Curso> optionalCurso = this.cursoRepository.findById(id);
        if(!optionalCurso.isPresent()){
            return null;
        }
        Curso cursoDB = optionalCurso.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumnoBO.getId());
        cursoDB.removeCursoAlumnos(cursoAlumno);

        return this.cursoRepository.save(cursoDB);
    }

    @Override
    public Curso eliminarExamen(Long id, ExamenBO examen) {
        Optional<Curso> optionalCurso = this.cursoRepository.findById(id);
        if(!optionalCurso.isPresent()){
            return null;
        }
        Curso cursoDB = optionalCurso.get();
        cursoDB.removeExamen(ExamenMapper.INSTANCE.ExamenToExamenBO(examen));
        return  this.cursoRepository.save(cursoDB);
    }

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        Curso curso = this.cursoRepository.findCursoByAlumnoId(id);
        if(curso != null){
            ResponseResult<Iterable<Long>> result = this.obtenerIdPorAlumno(id);
            if(result != null && result.getOperationStatus() && result.getResult() != null){
                List<Long> examenesId = (List<Long>) result.getResult();
                if(examenesId != null && !examenesId.isEmpty()){
                    List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                        if(examenesId.contains(examen.getId())){
                            examen.setRespondido(true);
                        }
                        return examen;
                    }).collect(Collectors.toList());
                    curso.setExamenes(examenes);
                }
            }
        }
        return curso;
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        this.cursoAlumnoRepository.eliminarCursoAlumnoPorId(id);
    }


    private ResponseResult<Iterable<Long>> obtenerIdPorAlumno(Long alumnoId) {
        return this.responseFeignClient.obtenerIdPorAlumno(alumnoId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.cursoRepository.deleteById(id);
    }

    @Override
    public ResponseSearchResult<List<Curso>> findAll(Pageable pageable) {
        Page<Curso> cursoPage = cursoRepository.findAll(pageable);
        if(cursoPage == null ){
            return null;
        }

        Page<Curso> cursoPageMod = cursoPage.map(c->{
            c.getCursoAlumnos().forEach(ca->{
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                c.addAlumnos(alumno);
            });
            return c;
        });

        return new ResponseSearchResult<List<Curso>>(cursoPageMod.getContent(), cursoPage.getTotalPages(), cursoPage.getTotalElements());
    }

}
