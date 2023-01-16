package learn.spring.student.common;


import learn.spring.student.constants.EnumStatusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityResponse<T> {
    private EnumStatusResponse status;
    private String message;
    private T data;
}