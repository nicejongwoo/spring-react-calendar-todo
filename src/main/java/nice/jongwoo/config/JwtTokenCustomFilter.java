package nice.jongwoo.config;

import lombok.RequiredArgsConstructor;
import nice.jongwoo.member.MemberDetails;
import nice.jongwoo.member.MemberRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
//@RequiredArgsConstructor
public class JwtTokenCustomFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthUserDetailsService authUserDetailsService;

    @Autowired
    public JwtTokenCustomFilter(JwtTokenUtils jwtTokenUtils, AuthUserDetailsService authUserDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        //인증헤더 가져오기, 유효성 검사
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //jwt token 가져오기 & 유효성 검사
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtils.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        //인증완료 처리
        //Get user identity and set it on the spring security context
        String email = jwtTokenUtils.getEmail(token);
//        String userName = jwtTokenUtils.getUserName(token);
        MemberDetails memberDetails = authUserDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            memberDetails,
            null,
            memberDetails == null ? List.of() : memberDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);

    }
}
