package org.mymoney.enums;

public enum ErrorCode {
    COMMAND_PARSING_ERROR(3001, "PARSING ERROR : Failed to parse command name "),
    COMMAND_ARGS_PARSING_ERROR(3002, "ARGS PARSING ERROR : Failed to parse command arguments "),
    FILE_ACCESS_ERROR(3003, "FILE ACCESS ERROR  : Failed to open/parse file "),
    FILE_READ_ERROR(3005, "FILE READ ERROR  : Error in reading file "),
    RUNTIME_ERROR(500, "RUNTIME_ERROR : Run time error");

    private final int errorCode;
    private final String errorMessage;

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
