package com.ozenero.webparse.exception;

public class IWebParseException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  protected final transient ErrorCode errorCode;
  
  public static final String BAD_REQUEST_CODE = "-1000000";

  public IWebParseException(ErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}