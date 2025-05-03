package goormton.backend.web1team.domain.User.service;

import goormton.backend.web1team.domain.User.domain.User;
import goormton.backend.web1team.domain.User.domain.UserRole;
import goormton.backend.web1team.domain.User.domain.repository.UserRepository;
import goormton.backend.web1team.domain.User.dto.request.LoginRequest;
import goormton.backend.web1team.domain.User.dto.request.SignupRequest;
import goormton.backend.web1team.domain.User.dto.response.JwtResponse;
import goormton.backend.web1team.global.config.security.jwt.JwtUtil;
import goormton.backend.web1team.global.error.DefaultException;
import goormton.backend.web1team.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void addUser(SignupRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    public JwtResponse signupAndLogin(SignupRequest request) {
        addUser(request);

        return loginAndGetToken(new LoginRequest(request.username(), request.password()));
    }

    public JwtResponse loginAndGetToken(LoginRequest request) {
//        사용자 인증
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        jwt 생성
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + request.username()));
        String jwt = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().toString());

        return new JwtResponse(jwt, "Bearer");
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_PARAMETER));
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public boolean verifyCurrentPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
