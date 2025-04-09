package goormton.backend.web1team.global.config.security;

import goormton.backend.web1team.global.config.security.jwt.JwtFilter;
import goormton.backend.web1team.global.config.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/api-docs",
            "/swagger-ui-custom.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtUtil, userDetailsService);

        http
//                cors 설정
                .cors(Customizer.withDefaults())
//                crsf disable
                .csrf(AbstractHttpConfigurer::disable)
//                Form 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)
//                Http Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
//                세션 설정: STATELESS
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                경로별 인가 작업
                .authorizeHttpRequests((auth) -> auth
//                              미리 정의된 whitelist 경로는 모두 허용
                                .requestMatchers(AUTH_WHITELIST).permitAll()
//                              모든 get method는 허용
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                              유저 인증용 post method는 인증 없이 허용
                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//                              그 외의 모든 post method는 인증 필요
                                .requestMatchers(HttpMethod.POST, "/**").authenticated()
//                                혹시 모를 다른 모든 요청 역시 인증 필요
                                .anyRequest().authenticated()
                )
//                jwt 필터 설정
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
//                로그아웃 설정 추가
//                .logout(logout -> logout
//                        .logoutUrl("/api/auth/logout")
//                        .invalidateHttpSession(true)
//                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
