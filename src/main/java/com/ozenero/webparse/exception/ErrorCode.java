package com.ozenero.webparse.exception;

import java.util.List;

public interface ErrorCode {
  String getErrorCode();
  String getErrorMessage();
  List<String> getErrors();
  int getHttpStatusCode();
}
