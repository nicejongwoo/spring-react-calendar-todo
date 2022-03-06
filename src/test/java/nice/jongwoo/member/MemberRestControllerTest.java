package nice.jongwoo.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import nice.jongwoo.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"test"})
@WebMvcTest(controllers = MemberRestController.class)
@AutoConfigureMockMvc
class MemberRestControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        request.setPassword(password);

        Member member = MemberRequest.toEntity(request);

        given(memberService.registerMember(any())).willReturn(member);

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            ;

        //then - verify the output
        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is("회원가입이 완료되었습니다.")));

    }

}
