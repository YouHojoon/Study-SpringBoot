package ac.kr.smu.studyspringboot;

import ac.kr.smu.domain.Board;
import ac.kr.smu.domain.User;
import ac.kr.smu.domain.enums.BoardType;
import ac.kr.smu.repository.BoardRepository;
import ac.kr.smu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class StudyspringbootApplicationTests {
    Logger log = LoggerFactory.getLogger(StudyspringbootApplicationTests.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void init() {
        User user=userRepository.save(User.builder().name("havi").password("test")
                .email("test@gmail.com").createdDate(LocalDateTime.now()).build());
        boardRepository.save(Board.builder().title("테스트").subTitle("서브 타이틀").content("콘텐츠")
                .boardType(BoardType.free).createdDate(LocalDateTime.now()).updatedDate(LocalDateTime.now()).user(user).build());

    }

    @Test
    public void test() {
        User user=userRepository.findByEmail("test@gmail.com");
        assertThat(user.getName(),is("havi"));
        assertThat(user.getPassword(),is("test"));
        assertThat(user.getEmail(),is("test@gmail.com"));


    }

}
