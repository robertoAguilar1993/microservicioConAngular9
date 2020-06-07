package com.formacionbdi.microservicios.app.respuestas.services;

import com.formacionbdi.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.formacionbdi.microservicios.commons.controllers.ResponseResult;
import com.formacionbdi.microservicios.commons.examenes.bussines.ExamenBO;
import com.formacionbdi.microservicios.commons.examenes.bussines.PreguntaBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author beto
 */
@Service("respuestaServiceImpl")
public class RespuestaServiceImpl implements RespuestaService {

    private RespuestaRepository respuestaRepository;
    private ExamenFeignClient examenFeignClient;

    @Autowired
    public RespuestaServiceImpl(RespuestaRepository respuestaRepository, ExamenFeignClient examenFeignClient) {
        this.respuestaRepository = respuestaRepository;
        this.examenFeignClient = examenFeignClient;
    }

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestaBOS) {
        respuestaBOS = ( (List<Respuesta>) respuestaBOS)
                .stream()
                .map(resp->{
                    resp.setAlumnoId(resp.getAlumno().getId());
                    resp.setPreguntaId(resp.getPregunta().getId());
                    return  resp;
                })
                .collect(Collectors.toList());

        return this.respuestaRepository.saveAll(respuestaBOS);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByexamen(Long alumnoId, Long examenId) {
        ResponseResult<ExamenBO> responseResult= this.examenFeignClient.obtenerxamenPorId(examenId);
        if(responseResult != null && responseResult.getOperationStatus()){
            List<PreguntaBO> preguntas = responseResult.getResult().getPreguntas();
            List<Long> preguntasIds = preguntas.stream().map(p->p.getId()).collect(Collectors.toList());
            Iterable<Respuesta> respuesta = this.respuestaRepository.findRespuestaByAlumnoByPreguntasIds(alumnoId, preguntasIds);
            respuesta = ((List<Respuesta>) respuesta).stream().map(r->{
                preguntas.forEach(p->{
                    if(p.getId() == r.getPreguntaId()){
                        r.setPregunta(p);
                    }
                });
                return r;
            }).collect(Collectors.toList());
            return respuesta;
        }
        return null;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        List<Respuesta> respuestas = (List<Respuesta>) this.respuestaRepository.findByAlumnonId(alumnoId);
        List<Long> examenesIds = Collections.emptyList();
        if(respuestas != null && !respuestas.isEmpty()){
            List<Long> preguntasIds = respuestas.stream().map(r->r.getPreguntaId()).collect(Collectors.toList());

            if(preguntasIds != null && !preguntasIds.isEmpty()){
                ResponseResult<List<Long>> responseResult  = examenFeignClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntasIds);
                if(responseResult != null && responseResult.getOperationStatus()){
                    examenesIds = responseResult.getResult();
                }
            }
        }
        return examenesIds;
    }
}
