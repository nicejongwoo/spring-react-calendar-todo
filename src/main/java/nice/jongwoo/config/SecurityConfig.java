package nice.jongwoo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthUserDetailsService authUserDetailsService;
    private final JwtTokenCustomFilter jwtTokenCustomFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO configure authentication manager
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Enable CORS & disable CSRF
        http.cors().and().csrf().disable();

        //session stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //for h2-console
        http.headers().frameOptions().disable();

        //endpoint에 대한 권한
        http.authorizeRequests()
            .antMatchers("/api/v1/auth/**", "/h2-console/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
            .anyRequest().authenticated()
            ;
//            .antMatchers("/api/v1/todos**").authenticated()
//            .antMatchers("/api/v1/auth**").permitAll();

        //JWT Custom Filter 추가
        http.addFilterBefore(
            jwtTokenCustomFilter,
            UsernamePasswordAuthenticationFilter.class
        );

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
