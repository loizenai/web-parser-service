package com.ozenero.webparse.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum IWebParseErrorCode implements ErrorCode {
  WRONG_INPUT("1000001", "Wrong Input!", Arrays.asList("WRONG_INPUT!"), 400),
  LOADING_HTML_ERROR("2000001", "Loading Html Error", Arrays.asList("Loading Html Error!"), 500),
  PARSING_ERROR("3000001", "Parsing Error", Arrays.asList("Parsing Error!"), 500);
	
  @Getter private final String errorCode;
  @Getter private final String errorMessage;
  @Getter private final List<String> errors;
  @Getter private final int httpStatusCode;

  IWebParseErrorCode(String errorCode, String errorMessage, List<String> errors, int statusCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errors = errors;
    this.httpStatusCode = statusCode;
  }
}
