package com.formacionbdi.microservicios.commons.controllers;

import java.util.Map;

public class ResponseSearchResult<T> extends ResponseResult<T> {
    private Integer totalPages;
    private Long totalElementos;

    public ResponseSearchResult(T result, Integer totalPages, Long totalElementos) {
        super(result);
        this.totalPages = totalPages;
        this.totalElementos = totalElementos;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }
}
