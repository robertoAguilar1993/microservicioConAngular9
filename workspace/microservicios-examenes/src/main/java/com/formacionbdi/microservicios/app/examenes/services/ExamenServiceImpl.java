package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.controllers.ResponseSearchResult;
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
    public Iterable<Examen> findAll() {
        return this.examenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Examen> findById(Long id) {
        return this.examenRepository.findById(id);
    }

    @Override
    @Transactional
    public Examen save(Examen entity) {
        return this.examenRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.examenRepository.deleteById(id);
    }

    @Override
    public ResponseSearchResult<List<Examen>> findAll(Pageable pageable) {
        Page<Examen> examenPage = examenRepository.findAll(pageable);
        if(examenPage == null ){
            return null;
        }
        return new ResponseSearchResult<List<Examen>>(examenPage.getContent(), examenPage.getTotalPages(), examenPage.getTotalElements());
    }

    @Override
    @Transactional
    public Examen edit(Long id, Examen examen) {
        Optional<Examen> Examenoptional= this.examenRepository.findById(id);
        if(!Examenoptional.isPresent()){
            return null;
        }
        Examen examenDB = Examenoptional.get();
        examenDB.setNombre(examen.getNombre());

        try {
            List<Pregunta> preguntas = examenDB.getPreguntas()
                    .stream()
                    .filter(pdb->!examenDB.getPreguntas().contains(pdb))
                    .collect(Collectors.toList());

            if(preguntas != null && !preguntas.isEmpty()){
                preguntas.forEach(examenDB::removePreguntas);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        examenDB.setPreguntas(examen.getPreguntas());
        examenDB.setAsignaturaPadre(examen.getAsignaturaPadre());
        examenDB.setAsignaturaHija(examen.getAsignaturaHija());
        return  this.examenRepository.save(examenDB);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return  this.examenRepository.findByNombre(term);
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



}
