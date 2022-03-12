package nice.jongwoo.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member registerMember(Member member) {

        String email = member.getEmail();
        if (memberRepository.existsByEmail(email)) {
            log.error("Email already exists: {}", email);
            throw new RuntimeException("Email already exists");
        }

        return memberRepository.save(member);
    }

    @Override
    public Member getByCredentials(MemberRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(
//            () -> new UsernameNotFoundException(String.format("%s 로 가입된 회원이 없습니다.", request.getEmail()))
            () -> new UsernameNotFoundException("회원 정보가 일치하지 않습니다.")
        );

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("회원 정보가 일치하지 않습니다.");
        }

        return member;
    }

}
