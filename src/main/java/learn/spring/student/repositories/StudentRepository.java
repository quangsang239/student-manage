package learn.spring.student.repositories;

import learn.spring.student.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<StudentModel, Integer> {
    @Query("select s from StudentModel s where s.studentCode = :code")
    Optional<StudentModel> findByStudentCode(@Param("code") String code);
}
