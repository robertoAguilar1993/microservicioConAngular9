package com.formacionbdi.microservicios.app.respuestas.models.repository;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author beto
 */
public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

    @Query("{'alumnoId': ?0, 'preguntaId': {$in: ?1} }")
    public Iterable<Respuesta> findRespuestaByAlumnoByPreguntasIds(Long alumnoId, Iterable<Long> preguntaIds);

    @Query("{'alumnoId': ?0}")
    public Iterable<Respuesta> findByAlumnonId(Long alumnoId);
    //@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId = ?1 and e.id = ?2")
    //public Iterable<Respuesta> findRespuestaByAlumnoByexamen(Long alumnoId, Long examenId);

    //@Query("select e.id from Respuesta r  join r.pregunta p join p.examen e where r.alumnoId =?1 group by e.id")
    //public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);



}
