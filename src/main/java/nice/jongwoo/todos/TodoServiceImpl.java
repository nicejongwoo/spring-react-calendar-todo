package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
