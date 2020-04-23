package ac.kr.smu.studyspringboot;

import ac.kr.smu.domain.Board;
import ac.kr.smu.domain.User;
import ac.kr.smu.domain.enums.BoardType;
import ac.kr.smu.repository.BoardRepository;
import ac.kr.smu.repository.UserRepository;
import ac.kr.smu.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Handler;
import java.util.stream.IntStream;

@SpringBootApplication
@EntityScan(basePackages = "ac.kr.smu.domain")
@EnableJpaRepositories(basePackages = "ac.kr.smu.repository")
@ComponentScan(basePackages = "ac.kr.smu")
public class StudyspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyspringbootApplication.class, args);
    }

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception {
        return (args) -> {
            User user = userRepository.save(User.builder().name("havi").password("test")
                    .email("havi@gmail.com").createdDate(LocalDateTime.now()).build());
            IntStream.rangeClosed(1, 200).forEach(index -> boardRepository.save(Board.builder().title("게시글" + index)
                    .subTitle("순서" + index).content("콘텐트").boardType(BoardType.free).createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now()).user(user).build()));
        };
    }
}
