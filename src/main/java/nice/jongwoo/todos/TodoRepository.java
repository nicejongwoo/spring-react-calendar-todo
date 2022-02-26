package nice.jongwoo.todos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTodoDate(String todoDate);

    Optional<Todo> findByTodoToken(String todoToken);
}
