package nice.jongwoo.util;

import nice.jongwoo.config.AuthUserDetailsService;
import nice.jongwoo.config.JwtTokenCustomFilter;
import nice.jongwoo.config.JwtTokenUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;


public class CommonRestControllerMock {


    @MockBean
    protected JwtTokenCustomFilter jwtTokenCustomFilter;

    @MockBean
    protected JwtTokenUtils jwtTokenUtils;

    @MockBean
    protected AuthUserDetailsService authUserDetailsService;

    @MockBean
    protected AuthenticationManager authenticationManager;
}
