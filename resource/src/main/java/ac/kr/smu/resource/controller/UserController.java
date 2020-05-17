package ac.kr.smu.resource.controller;

import ac.kr.smu.oauth2.domain.User;
import ac.kr.smu.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/users")
    public List<User> findAllUser(){
        return userRepository.findAll();
    }
}
