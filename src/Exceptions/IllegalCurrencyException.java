package Exceptions;

public class IllegalCurrencyException extends IllegalArgumentException {
    private String message;

    public IllegalCurrencyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
