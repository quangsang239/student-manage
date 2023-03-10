package learn.spring.student.repositories;

import learn.spring.student.entities.StudentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfoEntity, Integer> {
}
