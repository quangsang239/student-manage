package learn.spring.student.repositories;

import learn.spring.student.entities.UserEntity;
import learn.spring.student.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserModel> userExist(@Param("username") String username);

    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

}
