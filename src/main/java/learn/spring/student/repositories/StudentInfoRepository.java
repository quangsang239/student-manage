package learn.spring.student.repositories;

import learn.spring.student.entities.StudentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfoEntity, Integer> {
}
