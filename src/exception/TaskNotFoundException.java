package exception;

public class TaskNotFoundException extends Exception {

    String message;

    public TaskNotFoundException(String message){
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
