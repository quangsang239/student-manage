package learn.spring.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_info_model")
public class StudentInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id", nullable = false)
    private Integer infoId;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "student_id", foreignKey = @ForeignKey(name = "student_id"), name = "student_id",
            nullable = false, unique = true)
    @JsonIgnore
    private StudentModel studentModel;

    @Column(name = "address")
    private String address;

    @Column(name = "average_score")
    private Double averageScore;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

}