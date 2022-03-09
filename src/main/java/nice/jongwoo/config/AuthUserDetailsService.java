package nice.jongwoo.config;

import nice.jongwoo.member.Member;
import nice.jongwoo.member.MemberDetails;
import nice.jongwoo.member.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public AuthUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException(String.format("$s 로 가입된 회원이 없습니다."))
        );

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole()));

        return new MemberDetails(member, authorities);
    }
}
