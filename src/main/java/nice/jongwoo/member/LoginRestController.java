package nice.jongwoo.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nice.jongwoo.config.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/signin")
@RequiredArgsConstructor
public class LoginRestController {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> login(@RequestBody @Valid MemberRequest request) {
        try {

            Member member = memberService.getByCredentials(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, jwtTokenUtils.generateAccessToken(member));
            return ResponseEntity.ok()
                .headers(headers)
                .body(member);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
