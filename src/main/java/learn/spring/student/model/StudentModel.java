package learn.spring.student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentModel {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;

    @NotNull(message = "Field student name is null!")
    @NotBlank(message = "Field student name is empty!")
    @Size(max = 20, message = "Field is over 20 char!")
    @Column(name = "student_name", nullable = false, length = 20)
    private String studentName;

    @NotNull(message = "Field student code is null!")
    @NotBlank(message = "Field student code is empty!")
    @Size(max = 10, message = "Field is over 10 char!")
    @Column(name = "student_code", nullable = false, length = 10)
    private String studentCode;

    @OneToOne(mappedBy = "studentModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentInfoModel studentInfoModel;
}

