package learn.spring.student.exception;

public class CreateFileException extends RuntimeException{
    public CreateFileException (String message){
        super(message);
    }
}
