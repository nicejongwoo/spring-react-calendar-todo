package nice.jongwoo.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import nice.jongwoo.util.CommonRestControllerMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"test"})
@WebMvcTest(controllers = LoginRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginRestControllerTest extends CommonRestControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test Login Success")
    @Test
    void givenEmailPassword_whenLogin_thenSuccessLogin() throws Exception {
        //given - precondition ro setup
        String email = "tester@gmail.com";
        String userName = "tester";
        String password = "123456789";

        MemberRequest request = new MemberRequest();
        request.setEmail(email);
        request.setUserName(userName);
        request.setPassword(password);

        Member member = MemberRequest.toEntity(request);

        given(memberService.getByCredentials(any())).willReturn(member);
        given(jwtTokenUtils.generateAccessToken(any())).willReturn("test.test.test");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, jwtTokenUtils.generateAccessToken(member));

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .headers(headers)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(request)));

        //then - verify the output
        response
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().exists(HttpHeaders.AUTHORIZATION));

    }

}
