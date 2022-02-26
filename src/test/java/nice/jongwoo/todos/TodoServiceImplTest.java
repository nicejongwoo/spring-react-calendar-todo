package nice.jongwoo.todos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles(value = {"test"})
@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private final String TITLE = "test";
    private final String TODO_DATE = "2022-01-31";
    private Todo todo;
    private Todo todo2;

    @BeforeEach
    void setUp() {
        todo = Todo.builder()
            .title(TITLE)
            .todoDate(TODO_DATE)
            .build();

        todo2 = Todo.builder()
            .title(TITLE)
            .todoDate(TODO_DATE)
            .build();
    }

    @DisplayName("service test: 투두 등록")
    @Test
    void givenTodoObject_whenRegisterTodo_thenReturnSavedTodoToken() {
        //given - precondition ro setup
        given(todoRepository.save(any())).willReturn(todo);

        //when - action or the behaviour that we are going test
        String savedTodoToken = todoService.registerTodo(todo);

        //then - verify the output
        assertThat(savedTodoToken).isNotNull();
    }


    @DisplayName("service test: 선택된 날짜에 해당하는 투두 목록 조회")
    @Test
    void givenTodoList_whenFindAllByTodoDate_thenTodoListSelectedDate() {
        //given - precondition ro setup
        given(todoRepository.findByTodoDate(TODO_DATE)).willReturn(List.of(todo, todo2));

        //when - action or the behaviour that we are going test
        List<Todo> todoList = todoService.findAllByTodoDate(TODO_DATE);

        //then - verify the output
        assertThat(todoList.size()).isEqualTo(2);
    }

    @DisplayName("service test: 투두토큰으로 조회")
    @Test
    void givenTodo_whenFindByTodoToken_thenTodo() {
        //given - precondition ro setup
        given(todoRepository.findByTodoToken(todo.getTodoToken())).willReturn(Optional.ofNullable(todo));

        //when - action or the behaviour that we are going test
        Todo getTodo = todoService.findByTodoToken(todo.getTodoToken());

        //then - verify the output
        assertThat(getTodo.getTitle()).isEqualTo(TITLE);
    }

    @DisplayName("service test: 투두 수행완료로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnTrue_thenUpdateDoneTrue() {
        //given - precondition ro setup
        given(todoRepository.findByTodoToken(todo.getTodoToken())).willReturn(Optional.ofNullable(todo));

        //when - action or the behaviour that we are going test
        todoService.changeOnTrue(todo.getTodoToken());
        Todo updatedTodo = todoService.findByTodoToken(todo.getTodoToken());

        //then - verify the output
        assertThat(updatedTodo.isDone()).isTrue();
    }

    @DisplayName("service test: 투두 수행 중으로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnFalse_thenUpdateDoneFalse() {
        //given - precondition ro setup
        given(todoRepository.findByTodoToken(todo.getTodoToken())).willReturn(Optional.ofNullable(todo));

        //when - action or the behaviour that we are going test
        todoService.changeOnFalse(todo.getTodoToken());
        Todo updatedTodo = todoService.findByTodoToken(todo.getTodoToken());

        //then - verify the output
        assertThat(updatedTodo.isDone()).isFalse();
    }

    @DisplayName("service test: 투두 title, todoDate 수정")
    @Test
    void givenTodoObject_whenEditTodo_thenUpdatedTodo() {
        //given - precondition ro setup
        given(todoRepository.findByTodoToken(todo.getTodoToken())).willReturn(Optional.ofNullable(todo));

        String modifyTitle = "modify test";
        String modifyTodoDate = "2022-03-01";

        Todo modifyTodo = Todo.builder()
            .title(modifyTitle)
            .todoDate(modifyTodoDate)
            .build();

        //when - action or the behaviour that we are going test
        Todo updatedTodo = todoService.editTodo(modifyTodo, todo.getTodoToken());

        //then - verify the output
        assertThat(updatedTodo.getTitle()).isEqualTo(modifyTitle);
        assertThat(updatedTodo.getTodoDate()).isEqualTo(modifyTodoDate);
    }

    @DisplayName("service test: 투두 제거")
    @Test
    void givenTodoObject_whenDelete_thenRemoveTodo() {
        //given - precondition ro setup
        given(todoRepository.findByTodoToken(todo.getTodoToken())).willReturn(Optional.ofNullable(todo));

        //when - action or the behaviour that we are going test
        todoService.deleteByTodoToken(todo.getTodoToken());

        //then - verify the output
        verify(todoRepository, times(1)).delete(todo);
    }

}
