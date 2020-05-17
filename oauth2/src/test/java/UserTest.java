import ac.kr.smu.oauth2.OauthApplication;
import ac.kr.smu.oauth2.domain.User;
import ac.kr.smu.oauth2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootTest(classes = OauthApplication.class)
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertUser(){
        userRepository.save(User.builder().id("user").passwd(passwordEncoder.encode("pass"))
        .name("유호준").roles(Collections.singletonList("ROLE_USER")).build());
    }
}
