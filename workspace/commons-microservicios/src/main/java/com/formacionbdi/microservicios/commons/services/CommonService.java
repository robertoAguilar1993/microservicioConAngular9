package com.formacionbdi.microservicios.commons.services;

import com.formacionbdi.microservicios.commons.controllers.ResponseSearchResult;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author beto
 */
public interface CommonService<E> {

    /**
     * @return
     */
    Iterable<E> findAll();

    /**
     * @param id
     * @return
     */
    Optional<E> findById(Long id);

    /**
     * @param entity
     * @return
     */
    E save(E entity);

    /**
     * @param id
     */
    void deleteById(Long id);


    ResponseSearchResult<List<E>> findAll(Pageable pageable);

}
