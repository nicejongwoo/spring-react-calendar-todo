package nice.jongwoo.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nice.jongwoo.config.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody @Valid MemberRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {

            Member member = memberService.getByCredentials(request);

            HttpHeaders headers = new HttpHeaders();
            String accessToken = jwtTokenUtils.generateAccessToken(member);
            String refreshToken = jwtTokenUtils.generateRefreshToken();
            headers.set(HttpHeaders.AUTHORIZATION, accessToken);

            response.put("message", "로그인 되었습니다");
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok()
                .headers(headers)
                .body(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberRequest memberRequest) throws URISyntaxException {
        Map<String, Object> response = new HashMap<>();

        try{
            memberRequest.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
            Member member = MemberRequest.toEntity(memberRequest);
            Member savedMember = memberService.registerMember(member);
            response.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.created(new URI("/api/v1/members/" + savedMember.getUserToken())).body(response);
        }catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


}
