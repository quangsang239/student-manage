package learn.spring.student.repositories;

import learn.spring.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Query("select s from StudentEntity s where s.studentCode = :code")
    Optional<StudentEntity> findByStudentCode(@Param("code") String code);
}
