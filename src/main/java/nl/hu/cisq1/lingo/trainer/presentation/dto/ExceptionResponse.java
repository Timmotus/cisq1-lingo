package nl.hu.cisq1.lingo.trainer.presentation.dto;

public class ExceptionResponse {
    private final String exception;

    public ExceptionResponse(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return this.exception;
    }
}
