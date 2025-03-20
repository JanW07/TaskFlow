package com.taskflow.taskflowbackend.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "InternalError"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "UserNotFound"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TaskNotFound");


    private final HttpStatus httpStatus;
    private final String messageCode;

    ErrorCode(HttpStatus httpStatus, String messageCode) {
        this.httpStatus = httpStatus;
        this.messageCode = messageCode;
    }
}
