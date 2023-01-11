package learn.spring.student.repositories;

import learn.spring.student.model.StudentInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentInfoRepository extends JpaRepository<StudentInfoModel, Integer> {
}
