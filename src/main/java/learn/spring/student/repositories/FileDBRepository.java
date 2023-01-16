package learn.spring.student.repositories;

import learn.spring.student.entities.FileDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDBEntity, Integer> {

}
