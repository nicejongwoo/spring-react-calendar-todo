package nice.jongwoo.common;

import nice.jongwoo.member.Member;
import nice.jongwoo.member.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        MemberDetails member = (MemberDetails) authentication.getPrincipal();
        String email = member.getMember().getEmail();

        return Optional.of(email);
    }
}
