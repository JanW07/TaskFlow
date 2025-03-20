package com.taskflow.taskflowbackend.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskFlowException.class)
    public ResponseEntity<ErrorResponse> handleTaskFlowException(TaskFlowException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.name(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    // Optionally, handle other exceptions here:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL.name(),
                "An unexpected error occurred."
        );
        return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL.getHttpStatus());
    }

    // A simple error response DTO for consistent error messages.
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
