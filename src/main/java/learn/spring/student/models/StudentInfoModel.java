package learn.spring.student.models;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoModel {
    private Integer infoId;
    private StudentModel studentModel;
    private String address;
    private Double averageScore;
    private Date dateOfBirth;

}