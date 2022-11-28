package com.ozenero.webparse.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<T> {
	private String errorCode;
    private String message;
    private List<T> errors;

    public ApiError(String errorCode, String message, List<T> errors) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String errorCode, String message, T error) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
