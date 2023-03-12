package exception;

public class IncorrectArgumentException extends Exception {

    String message;

    public IncorrectArgumentException (String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
