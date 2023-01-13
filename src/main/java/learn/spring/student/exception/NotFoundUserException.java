package learn.spring.student.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String message){
        super(message);
    }
}
