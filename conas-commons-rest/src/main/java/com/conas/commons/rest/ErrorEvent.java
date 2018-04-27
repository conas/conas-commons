package com.conas.commons.rest;

import com.conas.commons.rest.error.ErrorBuilder;


public interface ErrorEvent {
    int code();
    String message();

    default ErrorBuilder format(Object value){
        return new ErrorBuilder(this).format(value);
    }
}
