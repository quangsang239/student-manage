package learn.spring.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandle {
    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundUserException ex){
        return new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleInvalidException(TokenInvalidException ex){
        return new ExceptionResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(CreateFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleCreateFileException(CreateFileException ex){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(StudentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleStudentException(StudentException ex){
        return new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

}
