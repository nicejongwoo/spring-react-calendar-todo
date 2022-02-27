package nice.jongwoo.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"test"})
@WebMvcTest
class TodoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoFacade todoFacade;

    @Autowired
    private ObjectMapper objectMapper;

    private final String TITLE = "test";
    private final String TODO_DATE = "2022-01-31";

    @DisplayName("controller test: 투두 등록")
    @Test
    void givenTodoObject_whenRegisterTodo_thenReturnSavedTodoToken() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title(TITLE)
            .todoDate(TODO_DATE)
            .build();

        given(todoFacade.registerTodo(any())).willReturn(todo.getTodoToken());

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todo))
        );

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is("등록완료")))
        ;
    }

    @DisplayName("controller test: 선택된 날짜에 해당하는 투두 목록 조회")
    @Test
    void givenTodoList_whenFindAllByTodoDate_thenTodoListSelectedDate() throws Exception {
        //given - precondition ro setup
        List<Todo> todoList = new ArrayList<>();
        todoList.add(Todo.builder().title("test1").todoDate(TODO_DATE).build());
        todoList.add(Todo.builder().title("test2").todoDate(TODO_DATE).build());
        given(todoFacade.findByTodoDate(TODO_DATE)).willReturn(todoList);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/todos/{todoDate}", TODO_DATE));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(todoList.size())))
        ;
    }

    @DisplayName("controller test: 투두토큰으로 조회")
    @Test
    void givenTodo_whenFindByTodoToken_thenTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE).build();
        given(todoFacade.findByTodoToken(todo.getTodoToken())).willReturn(todo);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/todos/todo/{todoToken}", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("test")))
            .andExpect(jsonPath("$.todoDate", is(TODO_DATE)))
            .andExpect(jsonPath("$.message", is("success")))
        ;
    }

    @DisplayName("controller test: 투두 수행완료로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnTrue_thenUpdateDoneTrue() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE).build();

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/todos/todo/{todoToken}/done", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("success")))
        ;
    }

    @DisplayName("controller test: 투두 수행 중으로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnFalse_thenUpdateDoneFalse() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE).build();

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/todos/todo/{todoToken}/undone", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("success")))
        ;
    }

    @DisplayName("controller test: 투두 title, todoDate 수정")
    @Test
    void givenTodoObject_whenEditTodo_thenUpdatedTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE).build();

        TodoRequest request = new TodoRequest();
        request.setTitle("modify");
        request.setTodoDate("2022-12-30");
        Todo modifyTodo = TodoRequest.toEntity(request);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/todos/todo/{todoToken}", todo.getTodoToken())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(modifyTodo))
        );

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("success")))
        ;
    }

    @DisplayName("controller test: 투두 제거")
    @Test
    void givenTodoObject_whenDelete_thenRemoveTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE).build();
//        given(todoFacade.findByTodoToken(todo.getTodoToken())).willReturn(todo);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/todos/todo/{todoToken}", todo.getTodoToken())
        );

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("success")))
        ;
        verify(todoFacade, times(1)).removeTodo(todo.getTodoToken());
    }

}
