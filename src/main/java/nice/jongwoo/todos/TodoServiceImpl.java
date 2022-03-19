package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    @Override
    public String registerTodo(Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.getTodoToken();
    }

    @Override
    public List<Todo> findAllByTodoDate(String todoDate) {
        return todoRepository.findByTodoDate(todoDate);
    }

    @Override
    public Todo findByTodoToken(String todoToken) {
        Todo savedTodo = todoRepository.findByTodoToken(todoToken).orElseThrow(RuntimeException::new);
        return savedTodo;
    }

    @Override
    public void changeOnTrue(String todoToken) {
        Todo savedTodo = todoRepository.findByTodoToken(todoToken).orElseThrow(RuntimeException::new);
        savedTodo.changeOnTrue();
        todoRepository.save(savedTodo);
    }

    @Override
    public void changeOnFalse(String todoToken) {
        Todo savedTodo = todoRepository.findByTodoToken(todoToken).orElseThrow(RuntimeException::new);
        savedTodo.changeOnFalse();
        todoRepository.save(savedTodo);
    }

    @Override
    public Todo editTodo(Todo todo, String todoToken) {
        Todo savedTodo = todoRepository.findByTodoToken(todoToken).orElseThrow(RuntimeException::new);
        savedTodo.edit(todo.getTitle(), todo.getTodoDate());

        todoRepository.save(savedTodo);
        return savedTodo;
    }

    @Override
    public void deleteByTodoToken(String todoToken) {
        Todo savedTodo = todoRepository.findByTodoToken(todoToken).orElseThrow(RuntimeException::new);
        todoRepository.delete(savedTodo);
    }

    @Override
    public List<Todo> findAllMonthly(String startDate, String endDate) {
        return todoRepository.findAllByTodoDateBetweenStartDateAndEndDate(startDate, endDate);
    }

    @Override
    public List<Todo> findAllMonthlyByEmail(String startDate, String endDate, String email) {
        return todoRepository.findAllByTodoDateBetweenAndCreatedBy(startDate, endDate, email);
    }
}
