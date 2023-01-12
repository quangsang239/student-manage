package learn.spring.student.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false, unique = true)
    private Integer studentId;

    @Column(name = "student_name", nullable = false, length = 20)
    private String studentName;

    @Column(name = "student_code", nullable = false, length = 10)
    private String studentCode;

    @OneToOne(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentInfoEntity studentInfoEntity;
}

