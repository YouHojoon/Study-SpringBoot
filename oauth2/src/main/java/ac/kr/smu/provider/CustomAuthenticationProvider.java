package ac.kr.smu.provider;

import ac.kr.smu.domain.User;
import ac.kr.smu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String passwd = authentication.getCredentials().toString();
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));
        if(!passwordEncoder.matches(passwd,user.getPassword()))
            throw new BadCredentialsException("비밀번호가 맞지 않습니다.");
        return new UsernamePasswordAuthenticationToken(id, passwd, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
