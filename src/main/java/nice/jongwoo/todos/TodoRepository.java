package nice.jongwoo.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTodoDate(String todoDate);

    Optional<Todo> findByTodoToken(String todoToken);

    @Query(value = "select * from todo t where t.todo_date between :startDate and :endDate", nativeQuery = true)
    List<Todo> findAllByTodoDateBetweenStartDateAndEndDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Todo> findAllByTodoDateBetweenAndCreatedBy(String startDate, String endDate, String email);

    List<Todo> findByTodoDateAndCreatedBy(String todoDate, String email);
}
