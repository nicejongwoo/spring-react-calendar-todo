package nice.jongwoo.todos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nice.jongwoo.member.MemberDetails;
import nice.jongwoo.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoRestController {

    private final TodoFacade todoFacade;
    private final MemberService memberService;

    //C
    @PostMapping("")
    public ResponseEntity<?> registerTodo(@RequestBody @Valid TodoRequest todoRequest) throws URISyntaxException {
        log.debug("todoRequest:: {}", todoRequest);
        Todo todo = TodoRequest.toEntity(todoRequest);
        String todoToken = todoFacade.registerTodo(todo);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "새로 등록했어요.");
        response.put("todoToken", todoToken);

        return ResponseEntity.created(new URI("/api/v1/todos/" + todoToken)).body(response);
    }

    //R
    @GetMapping("")
    public ResponseEntity<?> getTodoListMonthly(@RequestParam String startDate,
                                                @RequestParam String endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();
        List<Todo> todoList = todoFacade.findAllMonthlyByEmail(startDate, endDate, principal.getMember().getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        response.put("todos", todoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{todoDate}")
    public ResponseEntity<?> getTodoListBySelectedDate(@PathVariable String todoDate) {
        List<Todo> todoList = todoFacade.findByTodoDate(todoDate);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        response.put("todos", todoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/todo/{todoToken}")
    public ResponseEntity<?> getTodoListByTodoToken(@PathVariable String todoToken) {
        Todo todo = todoFacade.findByTodoToken(todoToken);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        response.put("todoToken", todo.getTodoToken());
        response.put("title", todo.getTitle());
        response.put("todoDate", todo.getTodoDate());
        return ResponseEntity.ok(response);
    }

    //U
    @PutMapping("/todo/{todoToken}/done")
    public ResponseEntity<?> updateDoneTrue(@PathVariable String todoToken) {
        todoFacade.changeOnTrue(todoToken);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "다 했어요.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/todo/{todoToken}/undone")
    public ResponseEntity<?> updateDoneFalse(@PathVariable String todoToken) {
        todoFacade.changeOnFalse(todoToken);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "아직 끝나지 않았어요.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/todo/{todoToken}")
    public ResponseEntity<?> editTodo(@PathVariable String todoToken, @RequestBody @Valid TodoRequest todoRequest) {
        todoRequest.setTitle(todoRequest.getTitle());
        todoRequest.setTodoDate(todoRequest.getTodoDate());
        Todo modifyTodo = TodoRequest.toEntity(todoRequest);

        Todo modifiedTodo = todoFacade.editTodo(modifyTodo, todoToken);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "수정완료");
        return ResponseEntity.ok(response);
    }

    //D
    @DeleteMapping("/todo/{todoToken}")
    public ResponseEntity<?> removeTodo(@PathVariable String todoToken) {
        todoFacade.removeTodo(todoToken);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "삭제했어요.");
        return ResponseEntity.ok(response);
    }

}
