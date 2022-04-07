package nice.jongwoo.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import nice.jongwoo.config.AuthUserDetailsService;
import nice.jongwoo.member.Member;
import nice.jongwoo.member.MemberDetails;
import nice.jongwoo.member.MemberRequest;
import nice.jongwoo.member.MemberServiceImpl;
import nice.jongwoo.util.CommonRestControllerMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"test"})
@WebMvcTest(TodoRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TodoRestControllerTest extends CommonRestControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoFacade todoFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberServiceImpl memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String TITLE = "test";
    private final String TODO_DATE_2022_01_31 = "2022-01-31";

    @MockBean(name = "authUserDetailsService")
    private AuthUserDetailsService authUserDetailsService;

    @PostConstruct
    void setUp() {
        MemberRequest request = new MemberRequest();
        request.setEmail("test@email.com");
        request.setUserName("tester");
        request.setPassword(passwordEncoder.encode("123"));

        Member memberObject = MemberRequest.toEntity(request);
        given(authUserDetailsService.loadUserByUsername(anyString()))
            .willReturn(new MemberDetails(memberObject, List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @DisplayName("controller test: 투두 등록")
    @Test
    void givenTodoObject_whenRegisterTodo_thenReturnSavedTodoToken() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder()
            .title(TITLE)
            .todoDate(TODO_DATE_2022_01_31)
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
            .andExpect(jsonPath("$.message", is("새로 등록했어요.")))
        ;
    }

//    @DisplayName("controller test: 선택된 달에 해당하는 모든 투두 목록 조회")
//    @Test
//    void givenTodoList_whenFindAllByMonthly_thenTodoListSelectedMonth() throws Exception {
//        //given - precondition ro setup
//        List<Todo> todoList = new ArrayList<>();
//        todoList.add(Todo.builder().title("test1").todoDate(TODO_DATE_2022_01_31).build());
//        todoList.add(Todo.builder().title("test2").todoDate(TODO_DATE_2022_01_31).build());
//        given(todoFacade.findAllMonthly("2022-01-01", "2022-01-31")).willReturn(todoList);
//
//        //when - action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(get("/api/v1/todos?startDate=2022-01-31&endDate=2022-01-31"));
//
//        //then - verify the output
//        response.andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.size()", is(todoList.size())))
//        ;
//    }

    /*
     * @WithUserDetailse 참조
     * https://stackoverflow.com/questions/63419621/spring-boot-webmvctest-how-to-test-controller-method-with-authentication-object
     */
    @WithUserDetails(value = "tester", userDetailsServiceBeanName = "authUserDetailsService")
    @DisplayName("controller test: 선택된 달의 특정 사용자의 투두 목록 조회")
    @Test
    void givenTodoListEmail_whenFindAllByMonthly_thenTodoListSelectedMonth() throws Exception {
        //given - precondition ro setup
        String startDate = "2022-01-01";
        String endDate = "2022-01-31";
        String email = "test@email.com";
        List<Todo> todoList = new ArrayList<>();

        Todo test1 =
            Todo.builder().title("test1").todoDate(TODO_DATE_2022_01_31).build();
        test1.setCreatedBy(email);

        Todo test2 =
            Todo.builder().title("test2").todoDate(TODO_DATE_2022_01_31).build();
        test2.setCreatedBy("another@email.com");

        todoList.add(test1);

        given(todoFacade.findAllMonthlyByEmail(startDate, endDate, email)).willReturn(todoList);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc
            .perform(get("/api/v1/todos?startDate="+startDate+"&endDate="+endDate));

        //then - verify the output
        verify(todoFacade, times(1)).findAllMonthlyByEmail(startDate, endDate, email);

        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.todos", hasSize(1)))
        ;

    }

    @WithUserDetails(value = "tester", userDetailsServiceBeanName = "authUserDetailsService")
    @DisplayName("controller test: 선택된 날짜에 해당하는 투두 목록 조회")
    @Test
    void givenTodoList_whenFindAllByTodoDate_thenTodoListSelectedDate() throws Exception {
        //given - precondition ro setup
        List<Todo> todoList = new ArrayList<>();
        todoList.add(Todo.builder().title("test1").todoDate(TODO_DATE_2022_01_31).build());
        todoList.add(Todo.builder().title("test2").todoDate(TODO_DATE_2022_01_31).build());
        given(todoFacade.findByTodoDate(TODO_DATE_2022_01_31)).willReturn(todoList);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/todos/{todoDate}", TODO_DATE_2022_01_31));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(todoList.size())))
        ;
    }

    @WithUserDetails(value = "tester", userDetailsServiceBeanName = "authUserDetailsService")
    @DisplayName("controller test: 선택된 날짜에 해당하는 특정 유저의 투두 목록 조회")
    @Test
    void givenTodoListEmail_whenFindAllByTodoDate_thenTodoListSelectedDate() throws Exception {
        //given - precondition ro setup
        List<Todo> todoList = new ArrayList<>();
        String email = "test@email.com";

        Todo todo1 = Todo.builder().title("test1").todoDate(TODO_DATE_2022_01_31).build();
        Todo todo2 = Todo.builder().title("test2").todoDate(TODO_DATE_2022_01_31).build();

        todo1.setCreatedBy(email);
        todo2.setCreatedBy(email);

        todoList.add(todo1);
        todoList.add(todo2);

        given(todoFacade.findByTodoDateAndCreatedBy(TODO_DATE_2022_01_31, email)).willReturn(todoList);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/todos/{todoDate}", TODO_DATE_2022_01_31));

        //then - verify the output
        verify(todoFacade, times(1)).findByTodoDateAndCreatedBy(TODO_DATE_2022_01_31, email);
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.todos", hasSize(2)))
        ;
    }

    @DisplayName("controller test: 투두토큰으로 조회")
    @Test
    void givenTodo_whenFindByTodoToken_thenTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE_2022_01_31).build();
        given(todoFacade.findByTodoToken(todo.getTodoToken())).willReturn(todo);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/todos/todo/{todoToken}", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("test")))
            .andExpect(jsonPath("$.todoDate", is(TODO_DATE_2022_01_31)))
            .andExpect(jsonPath("$.message", is("success")))
        ;
    }

    @DisplayName("controller test: 투두 수행완료로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnTrue_thenUpdateDoneTrue() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE_2022_01_31).build();

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/todos/todo/{todoToken}/done", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("다 했어요.")))
        ;
    }

    @DisplayName("controller test: 투두 수행 중으로 업데이트")
    @Test
    void givenTodoObject_whenChangeOnFalse_thenUpdateDoneFalse() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE_2022_01_31).build();

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/todos/todo/{todoToken}/undone", todo.getTodoToken()));

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("아직 끝나지 않았어요.")))
        ;
    }

    @DisplayName("controller test: 투두 title, todoDate 수정")
    @Test
    void givenTodoObject_whenEditTodo_thenUpdatedTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE_2022_01_31).build();

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
            .andExpect(jsonPath("$.message", is("수정완료")))
        ;
    }

    @DisplayName("controller test: 투두 제거")
    @Test
    void givenTodoObject_whenDelete_thenRemoveTodo() throws Exception {
        //given - precondition ro setup
        Todo todo = Todo.builder().title("test").todoDate(TODO_DATE_2022_01_31).build();
//        given(todoFacade.findByTodoToken(todo.getTodoToken())).willReturn(todo);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/todos/todo/{todoToken}", todo.getTodoToken())
        );

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("삭제했어요.")))
        ;
        verify(todoFacade, times(1)).removeTodo(todo.getTodoToken());
    }

}
