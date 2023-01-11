package learn.spring.student.repositories;

import learn.spring.student.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query("select u from UserModel u where u.username = :username")
    Optional<UserModel> userExist(@Param("username") String username);

    @Query("select u from UserModel u where u.username = :username")
    Optional<UserModel> findByUsername(@Param("username") String username);

}
