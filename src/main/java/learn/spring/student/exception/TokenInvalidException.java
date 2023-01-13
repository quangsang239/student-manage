package learn.spring.student.exception;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException (String message){
        super(message);
    }
}
