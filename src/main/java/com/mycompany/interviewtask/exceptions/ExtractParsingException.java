package com.mycompany.interviewtask.exceptions;

/**
 * Исключение которое нигде не понадобилось, но оставлю, как пример реализации. Можно еще сделать глобальный хендлер, но
 * оставлю как есть.
 */
public class ExtractParsingException extends RuntimeException {
    public ExtractParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
