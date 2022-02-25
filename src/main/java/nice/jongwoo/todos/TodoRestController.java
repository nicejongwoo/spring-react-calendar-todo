package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoRestController {

    private final TodoFacade todoFacade;

    //C
    @PostMapping
    public ResponseEntity<?> registerTodo(@RequestBody @Valid TodoRequest todoRequest) {
        log.debug("todoRequest:: {}", todoRequest);
        Todo todo = TodoRequest.toEntity(todoRequest);
        String todoToken = todoFacade.registerTodo(todo);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "등록완료");
        response.put("todoToken", todoToken);
        return ResponseEntity.ok().body(response);
    }

    //R
    //U
    //D
}
