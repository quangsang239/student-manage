package learn.spring.student.exception;

public class FileUploadNotSupport extends RuntimeException{
    public FileUploadNotSupport(String message){
        super(message);
    }
}
