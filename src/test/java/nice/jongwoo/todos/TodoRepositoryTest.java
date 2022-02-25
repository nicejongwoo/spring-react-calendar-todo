package nice.jongwoo.todos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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

}
