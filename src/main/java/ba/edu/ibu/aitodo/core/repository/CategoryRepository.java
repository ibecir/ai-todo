package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
