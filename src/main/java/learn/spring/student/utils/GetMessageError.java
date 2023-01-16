package learn.spring.student.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class GetMessageError {
    public static String getMessageError(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<ObjectError> listError;
            StringBuilder message = new StringBuilder();
            listError = bindingResult.getAllErrors();
            for (var error : listError) {
                message.append(error.getDefaultMessage());
            }
            return message.toString();
        }
        return null;
    }
}
