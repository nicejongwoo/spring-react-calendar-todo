package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoService todoService;

    public String registerTodo(Todo todo) {
        String todoToken = todoService.registerTodo(todo);
        return todoToken;
    }

    public List<Todo> findByTodoDate(String todoDate) {
        return todoService.findAllByTodoDate(todoDate);
    }

    public Todo findByTodoToken(String todoToken) {
        return todoService.findByTodoToken(todoToken);
    }

    public void changeOnTrue(String todoToken) {
        todoService.changeOnTrue(todoToken);
    }

    public void changeOnFalse(String todoToken) {
        todoService.changeOnFalse(todoToken);
    }

    public Todo editTodo(Todo todo, String todoToken) {
        Todo todo1 = todoService.editTodo(todo, todoToken);
        return todo1;
    }

    public void removeTodo(String todoToken) {
        todoService.deleteByTodoToken(todoToken);
    }

    public List<Todo> findAllMonthly(String startDate, String endDate) {
        return todoService.findAllMonthly(startDate, endDate);
    }

    public List<Todo> findAllMonthlyByEmail(String startDate, String endDate, String email) {
        return todoService.findAllMonthlyByEmail(startDate, endDate, email);
    }

    public List<Todo> findByTodoDateAndCreatedBy(String todoDate, String email) {
        return todoService.findAllByTodoDateAndEmail(todoDate, email);
    }
}
