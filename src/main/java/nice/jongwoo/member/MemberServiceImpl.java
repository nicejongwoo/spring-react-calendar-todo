package nice.jongwoo.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member registerMember(Member member) {

        String email = member.getEmail();
        if (memberRepository.existsByEmail(email)) {
            log.error("Email already exists: {}", email);
            throw new RuntimeException("Email already exists");
        }

        return memberRepository.save(member);
    }

}
