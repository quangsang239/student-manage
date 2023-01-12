package learn.spring.student.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {
    private Integer studentId;

    @NotNull(message = "Field student name is null!")
    @NotBlank(message = "Field student name is empty!")
    @Size(max = 20, message = "Field is over 20 char!")
    private String studentName;

    @NotNull(message = "Field student code is null!")
    @NotBlank(message = "Field student code is empty!")
    @Size(max = 10, message = "Field is over 10 char!")
    private String studentCode;
    private StudentInfoModel studentInfoModel;
}

