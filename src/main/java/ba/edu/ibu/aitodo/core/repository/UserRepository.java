package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.Task;
import ba.edu.ibu.aitodo.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
