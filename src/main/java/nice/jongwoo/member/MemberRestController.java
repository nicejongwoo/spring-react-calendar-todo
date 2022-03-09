package nice.jongwoo.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberRestController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //C
    @PostMapping
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberRequest memberRequest) throws URISyntaxException {

        memberRequest.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
        Member member = MemberRequest.toEntity(memberRequest);
        Member savedMember = memberService.registerMember(member);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원가입이 완료되었습니다.");

        return ResponseEntity.created(new URI("/api/v1/members/" + savedMember.getUserToken())).body(response);
    }

    //R
    //U
    //D
}
