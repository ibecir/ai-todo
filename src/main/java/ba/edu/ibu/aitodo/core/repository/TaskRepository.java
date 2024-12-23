package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByUser_Email(String email);

    @Query(value = "SELECT t FROM tasks t WHERE t.user.email = ?1")
    List<Task> getAllTasksByUserEmail(String email);
}
