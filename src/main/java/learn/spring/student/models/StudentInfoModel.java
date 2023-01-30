package learn.spring.student.models;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer infoId;
    private StudentModel studentModel;
    private String address;
    private Double averageScore;
    private Date dateOfBirth;

}