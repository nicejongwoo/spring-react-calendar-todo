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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = {"test"})
@WebMvcTest(controllers = AuthRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthRestControllerTest extends CommonRestControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberServiceImpl memberService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @DisplayName("controller test: 로그인성공")
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
        ResultActions response = mockMvc.perform(post("/api/v1/auth/signin")
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


    @WithMockUser
    @DisplayName("controller test: 회원가입")
    @Test
    void givenMemberRequest_whenRegisterMember_thenReturnSuccess() throws Exception {
        String email = "tester@gmail.com";
        String userName = "tester";
        String password = "123456789";

        //given - precondition ro setup
        MemberRequest request = new MemberRequest();
        request.setEmail(email);
        request.setUserName(userName);
        request.setPassword(passwordEncoder.encode(password));

        Member member = MemberRequest.toEntity(request);

        given(memberService.registerMember(any())).willReturn(member);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            ;

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is("회원가입이 완료되었습니다.")));

    }
}
