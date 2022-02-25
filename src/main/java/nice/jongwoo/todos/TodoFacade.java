package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoService todoService;

    public String registerTodo(Todo todo) {
        String todoToken = todoService.registerTodo(todo);
        return todoToken;
    }
}
