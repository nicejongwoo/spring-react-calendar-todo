package nice.jongwoo.todos;

import java.util.List;

public interface TodoService {

    String registerTodo(Todo todo);

    List<Todo> findAllByTodoDate(String todoDate);

    Todo findByTodoToken(String todoToken);

    void changeOnTrue(String todoToken);

    void changeOnFalse(String todoToken);

    Todo editTodo(Todo modifyTodo, String todoToken);

    void deleteByTodoToken(String todoToken);

    List<Todo> findAllMonthly(String startDate, String endDate);
}
