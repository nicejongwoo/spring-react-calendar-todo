package nice.jongwoo.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ActiveProfiles(value = {"test"})
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;


    @DisplayName("service test: 회원 정상가입")
    @Test
    void givenMember_whenRegisterMember_thenReturnSavedMember(){
        //given - precondition ro setup
        Member member = Member.builder()
            .email("tester@gmail.com")
            .userName("tester")
            .password("123456789")
            .build();
        given(memberRepository.save(any())).willReturn(member);

        //when - action or the behaviour that we are going test
        Member savedMember = memberService.registerMember(member);

        //then - verify the output
        assertThat(savedMember).isNotNull();
    }

    @DisplayName("service test: 회원 가입 - 이미존재")
    @Test
    void givenMember_whenRegisterMember_thenAlreadyEmail(){
        //given - precondition ro setup
        String email = "tester@gmail.com";
        Member member = Member.builder()
            .email(email)
            .userName("tester")
            .password("123456789")
            .build();
        given(memberRepository.existsByEmail(email)).willReturn(true);

        //when - action or the behaviour that we are going test
        String expectedMessage = "Email already exists";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            memberService.registerMember(member);
        });

        //then - verify the output
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

}
