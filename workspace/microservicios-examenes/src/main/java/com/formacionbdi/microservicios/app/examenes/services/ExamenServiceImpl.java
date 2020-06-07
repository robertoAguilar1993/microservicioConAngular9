package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.app.examenes.mappers.AsignaturaMappers;
import com.formacionbdi.microservicios.app.examenes.mappers.ExamenMapper;
import com.formacionbdi.microservicios.app.examenes.mappers.PreguntaMapper;
import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.controllers.ResponseSearchResult;
import com.formacionbdi.microservicios.commons.examenes.bussines.AsignaturaBO;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.bussines.PreguntaBO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("emamenServiceImpl")
public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository examenRepository;

    private AsignaturaRepository asignaturaRepository;

    @Autowired
    public ExamenServiceImpl(ExamenRepository examenRepository, AsignaturaRepository asignaturaRepository) {
        this.examenRepository = examenRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ExamenBO> findAll() {
        Iterable<Examen> examenIterable = this.examenRepository.findAll();
        if(examenIterable == null ){
            return null;
        }
        List<ExamenBO> examenBOList = new ArrayList<>();
        examenIterable.forEach(curso -> {
            examenBOList.add(ExamenMapper.INSTANCE.ExamenBOToExamen(curso));
        });
        return examenBOList;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamenBO> findById(Long id) {
        Optional<Examen>  examenOptional = this.examenRepository.findById(id);
        if(!examenOptional.isPresent()){
            return Optional.empty();
        }
        return Optional.of(ExamenMapper.INSTANCE.ExamenBOToExamen(examenOptional.get()));
    }

    @Override
    @Transactional
    public ExamenBO save(ExamenBO entity) {
        Examen examenDB =  this.examenRepository.save(ExamenMapper.INSTANCE.ExamenToExamenBO(entity));
        return ExamenMapper.INSTANCE.ExamenBOToExamen(examenDB);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.examenRepository.deleteById(id);
    }

    @Override
    public ResponseSearchResult<List<ExamenBO>> findAll(Pageable pageable) {
        Page<Examen> examenPage = examenRepository.findAll(pageable);
        if(examenPage == null ){
            return null;
        }
        List<ExamenBO> examenBOList = new ArrayList<>();
        examenPage.get().forEach(examen -> {
            examenBOList.add(ExamenMapper.INSTANCE.ExamenBOToExamen(examen));
        });

        return new ResponseSearchResult<List<ExamenBO>>(examenBOList, examenPage.getTotalPages(), examenPage.getTotalElements());
    }

    @Override
    @Transactional
    public ExamenBO edit(Long id, ExamenBO examen) {
        Optional<Examen> Examenoptional= this.examenRepository.findById(id);
        if(!Examenoptional.isPresent()){
            return null;
        }
        Examen examenDB = Examenoptional.get();
        examenDB.setNombre(examen.getNombre());

        try {
            examenDB.getPreguntas()
                    .stream()
                    .filter(pdb->!examenDB.getPreguntas().contains(PreguntaMapper.INSTANCE.PreguntaBOToPregunta(pdb)))
                    .forEach(examenDB::removePreguntas);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Pregunta> preguntas = examen.getPreguntas()
                .stream()
                .map(p->PreguntaMapper.INSTANCE.PreguntaToPreguntaBO(p))
                .collect(Collectors.toList());

        examenDB.setPreguntas(preguntas);
        return  ExamenMapper.INSTANCE.ExamenBOToExamen(this.examenRepository.save(examenDB));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamenBO> findByNombre(String term) {
        Iterable<Examen> examenIterable = this.examenRepository.findByNombre(term);
        if(examenIterable == null ){
            return null;
        }
        List<ExamenBO> examenBOS = new ArrayList<>();
        examenIterable.forEach(examen -> {
            examenBOS.add(ExamenMapper.INSTANCE.ExamenBOToExamen(examen));
        });
        return examenBOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return  this.asignaturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds) {
        return this.examenRepository.findExamenesIdsConRespuestasByPreguntaIds(preguntasIds);
    }

    /*private ExamenBO convert(Examen examen){
        ExamenBO examenBO = null;
        if(examen != null){
            examenBO = ExamenMapper.INSTANCE.ExamenBOToExamen(examen);
            if(examen.getPreguntas() != null && !examen.getPreguntas().isEmpty()){
                List<PreguntaBO> preguntaBOS = examen.getPreguntas()
                        .stream()
                        .map(pregunta -> PreguntaMapper.INSTANCE.PreguntaBOToPregunta(pregunta))
                        .collect(Collectors.toList());
                examenBO.setPreguntas(preguntaBOS);
            }
        }
        return examenBO;
    }*/


}
