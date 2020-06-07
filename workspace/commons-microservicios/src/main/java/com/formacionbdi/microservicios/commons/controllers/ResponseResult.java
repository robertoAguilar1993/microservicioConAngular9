package com.formacionbdi.microservicios.commons.controllers;

import java.util.Map;

public class ResponseResult<T> {
    private T result;
    private Map<String, Object> errors;
    private Boolean operationStatus;

    public ResponseResult(T result) {
        this.result = result;
        this.operationStatus = true;
    }

    public ResponseResult(T result, Map<String, Object> errors) {
        this.result = result;
        this.errors = errors;
        this.operationStatus = false;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public Boolean getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(Boolean operationStatus) {
        this.operationStatus = operationStatus;
    }
}
