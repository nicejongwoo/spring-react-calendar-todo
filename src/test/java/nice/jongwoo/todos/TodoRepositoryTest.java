package nice.jongwoo.todos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = {"test"})
@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("repository test: 투두 등록")
    @Test
    void givenTotoObject_whenSave_thenReturnSavedTodo(){
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title("test todo")
            .todoDate("2022-01-31")
            .build();

        //when - action or the behaviour that we are going test
        Todo savedTodo = todoRepository.save(todo);

        //then - verify the output
        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getId()).isGreaterThan(0);
    }

    @DisplayName("repository test: 선택된 달에 해당하는 투두 목록 조회")
    @Test
    void givenTodoList_whenFindAllMonthly_thenTodoListSelectedMonth(){
        //given - precondition ro setup
        String todoDate = "2022-01-31";
        Todo todo1 = Todo.builder()
            .title("test todo1")
            .todoDate(todoDate)
            .build();

        Todo todo2 = Todo.builder()
            .title("test todo2")
            .todoDate(todoDate)
            .build();

        Todo todo3 = Todo.builder()
            .title("test todo3")
            .todoDate("2022-02-01")
            .build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        //when - action or the behaviour that we are going test
        String startDate = "2022-01-01";
        String endDate = "2022-01-31";
        List<Todo> todoList = todoRepository.findAllByTodoDateBetweenStartDateAndEndDate(startDate, endDate);

        //then - verify the output
        assertThat(todoList).isNotNull();
        assertThat(todoList.size()).isEqualTo(2);
    }

    @DisplayName("repository test: 유저의 선택된 달에 투두 목록 조회")
    @Test
    void givenTodoListAndEmail_whenFindAllMonthly_thenTodoListSelectedMonth(){
        //given - precondition ro setup
        String todoDate = "2022-01-31";
        String email = "tester@email.com";
        String anotherEmail = "another@email.com";

        Todo todo1 = Todo.builder()
            .title("test todo1")
            .todoDate(todoDate)
            .build();

        Todo todo2 = Todo.builder()
            .title("test todo2")
            .todoDate(todoDate)
            .build();

        Todo todo3 = Todo.builder()
            .title("test todo3")
            .todoDate("2022-02-01")
            .build();

        todo1.setCreatedBy(email);
        todo2.setCreatedBy(anotherEmail);
        todo3.setCreatedBy(anotherEmail);

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        //when - action or the behaviour that we are going test
        String startDate = "2022-01-01";
        String endDate = "2022-01-31";
        List<Todo> todoList = todoRepository.findAllByTodoDateBetweenAndCreatedBy(startDate, endDate, email);

        //then - verify the output
        assertThat(todoList).isNotNull();
        assertThat(todoList.size()).isEqualTo(1);
    }

    @DisplayName("repository test: 선택된 날짜에 해당하는 투두 목록 조회")
    @Test
    void givenTodoList_whenFindAllByTodoDate_thenTodoListSelectedDate(){
        //given - precondition ro setup
        String todoDate = "2022-01-31";
        Todo todo1 = Todo.builder()
            .title("test todo1")
            .todoDate(todoDate)
            .build();

        Todo todo2 = Todo.builder()
            .title("test todo2")
            .todoDate(todoDate)
            .build();

        Todo todo3 = Todo.builder()
            .title("test todo3")
            .todoDate("2022-02-01")
            .build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        //when - action or the behaviour that we are going test
        List<Todo> totalTodoList = todoRepository.findAll();
        List<Todo> selectedTodoList = todoRepository.findByTodoDate(todoDate);

        //then - verify the output
        assertThat(selectedTodoList).isNotNull();
        assertThat(selectedTodoList.size()).isEqualTo(2);
        assertThat(selectedTodoList.size()).isLessThan(totalTodoList.size());
    }

    @DisplayName("repository test: 선택된 날짜(하루)에 해당하는 특정 유저의 투두 목록 조회")
    @Test
    void givenTodoListEmail_whenFindAllByTodoDate_thenTodoListSelectedDate(){
        //given - precondition ro setup
        String todoDate = "2022-01-31";
        Todo todo1 = Todo.builder()
            .title("test todo1")
            .todoDate(todoDate)
            .build();

        Todo todo2 = Todo.builder()
            .title("test todo2")
            .todoDate(todoDate)
            .build();

        todo1.setCreatedBy("test@email.com");
        todo2.setCreatedBy("another@email.com");

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        //when - action or the behaviour that we are going test
        List<Todo> selectedTodoList = todoRepository.findByTodoDateAndCreatedBy(todoDate, "test@email.com");

        //then - verify the output
        assertThat(selectedTodoList).isNotNull();
        assertThat(selectedTodoList.size()).isEqualTo(1);
    }


    @DisplayName("repository test: 투두토큰으로 조회")
    @Test
    void givenTodo_whenFindByTodoToken_thenTodo(){
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title("test")
            .todoDate("2022-01-31")
            .build();
        Todo savedTodo = todoRepository.save(todo);

        //when - action or the behaviour that we are going test
        Optional<Todo> getTodo = todoRepository.findByTodoToken(savedTodo.getTodoToken());

        //then - verify the output
        assertThat(getTodo.get().getTitle()).isEqualTo("test");
    }


    @DisplayName("repository test: 투두 수행완료로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnTrue_thenUpdateDoneTrue(){
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title("test")
            .todoDate("2022-01-31")
            .build();
        Todo savedTodo = todoRepository.save(todo);

        //when - action or the behaviour that we are going test
        savedTodo.changeOnTrue();
        Optional<Todo> updatedTodo = todoRepository.findByTodoToken(savedTodo.getTodoToken());

        //then - verify the output
        assertThat(updatedTodo.get().isDone()).isTrue();
        assertThat(updatedTodo.get().getTitle()).isEqualTo("test");
        assertThat(updatedTodo.get().getTodoDate()).isEqualTo("2022-01-31");
    }

    @DisplayName("repository test: 투두 수행 중으로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnFalse_thenUpdateDoneFalse(){
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title("test")
            .todoDate("2022-01-31")
            .build();
        Todo savedTodo = todoRepository.save(todo);
        savedTodo.changeOnTrue();
        Optional<Todo> updatedTodo = todoRepository.findByTodoToken(savedTodo.getTodoToken());

        //when - action or the behaviour that we are going test
        updatedTodo.get().changeOnFalse();

        //then - verify the output
        assertThat(updatedTodo.get().isDone()).isFalse();
        assertThat(updatedTodo.get().getTitle()).isEqualTo("test");
        assertThat(updatedTodo.get().getTodoDate()).isEqualTo("2022-01-31");
    }

    @DisplayName("repository test: 투두 제거")
    @Test
    void givenTodoObject_whenDelete_thenRemoveTodo(){
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title("test")
            .todoDate("2022-01-31")
            .build();
        Todo savedTodo = todoRepository.save(todo);

        //when - action or the behaviour that we are going test
        todoRepository.delete(savedTodo);
        Optional<Todo> getTodo = todoRepository.findByTodoToken(savedTodo.getTodoToken());
        List<Todo> todoList = todoRepository.findByTodoDate("2022-01-31");

        //then - verify the output
        assertThat(getTodo).isEmpty();
        assertThat(todoList.size()).isEqualTo(0);
    }

}
