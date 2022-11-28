package com.ozenero.webparse.advice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ozenero.webparse.exception.ApiError;
import com.ozenero.webparse.exception.ErrorCode;
import com.ozenero.webparse.exception.IWebParseException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ IWebParseException.class })
	public ResponseEntity<Object> handleAll(IWebParseException ex, WebRequest request) {
		ErrorCode errorCodeObj = ex.getErrorCode();

		String errorCode = errorCodeObj.getErrorCode();
		String message = errorCodeObj.getErrorMessage();
		List<String> errors = errorCodeObj.getErrors();

		ApiError<String> apiError = new ApiError<>(errorCode, message, errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.valueOf(errorCodeObj.getHttpStatusCode()));
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		String errorCode = "-5000000";
		String message = ex.getLocalizedMessage();
		String error = ex.getMessage();

		ApiError<String> apiError = new ApiError<>(errorCode, message, Arrays.asList(error));
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "Method Argument Not Valid Exception";
		List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

		ApiError<String> errors = new ApiError<>(IWebParseException.BAD_REQUEST_CODE, errorMessage, validationList);
		
		return new ResponseEntity<>(errors, new HttpHeaders(), status);
	}
}
