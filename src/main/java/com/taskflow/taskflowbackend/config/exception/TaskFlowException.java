package com.taskflow.taskflowbackend.config.exception;

public class TaskFlowException extends RuntimeException {

    private final ErrorCode errorCode;

    public TaskFlowException(ErrorCode errorCode, Object... messageArgs) {
        super(String.format(errorCode.getMessageCode(), messageArgs));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
