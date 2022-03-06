package nice.jongwoo.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles(value = {"test"})
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("repository test: 회원가입")
    @Test
    void givenMemberObject_whenSave_thenReturnSavedMember(){
        //given - precondition ro setup
        Member member = Member.builder()
            .email("tester@email.com")
            .userName("테스터")
            .password("123qwe!@#")
            .build();

        //when - action or the behaviour that we are going test
        Member savedMember = memberRepository.save(member);

        //then - verify the output
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isGreaterThan(0);
    }

    @DisplayName("repository test: 이메일 존재 확인")
    @Test
    void givenEqualEmail_whenExistsByEmail_thenTrue(){
        //given - precondition ro setup
        String email = "tester@email.com";
        Member member = Member.builder()
            .email(email)
            .userName("테스터")
            .password("123qwe!@#")
            .build();

        memberRepository.save(member);

        //when - action or the behaviour that we are going test
        boolean isExists = memberRepository.existsByEmail(email);

        //then - verify the output
        assertThat(isExists).isTrue();
    }

}
