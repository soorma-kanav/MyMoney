package org.mymoney.exception;

import org.mymoney.enums.ErrorCode;

public class MyMoneyException extends RuntimeException {

    private ErrorCode errorCode;

    public MyMoneyException(ErrorCode commandParsingError) {
        super(commandParsingError.getErrorMessage());
    }

    public MyMoneyException(ErrorCode commandParsingError, Throwable cause) {
        super(commandParsingError.getErrorMessage(), cause);
        this.errorCode = commandParsingError;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
