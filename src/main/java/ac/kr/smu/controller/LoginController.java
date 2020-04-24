package ac.kr.smu.controller;

import ac.kr.smu.annotation.SocialUser;
import ac.kr.smu.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Slf4j
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/loginSuccess")
    public String loginComplete(@SocialUser User user) {
        return "redirect:/board/list";
    }
}
