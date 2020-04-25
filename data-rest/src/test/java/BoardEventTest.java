import ac.kr.smu.DataRestApplication;
import ac.kr.smu.rest.config.SecurityConfig;
import ac.kr.smu.rest.domain.Board;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityConfig.class, DataRestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class BoardEventTest {
    private TestRestTemplate testRestTemplate = new TestRestTemplate("adminUser","admin");

    @Test
    public void saveTest(){
        Board createdBoard = createBoard();
        assertNotNull(createdBoard.getCreatedDate());
    }
    @Test
    public void updateTest(){
        Board createdBoard = createBoard();
        Board updateBoard = updateBoard(createdBoard);
        assertNotNull(updateBoard.getUpdatedDate());
    }
    private Board createBoard(){
        Board board= Board.builder().title("test").build();
        return testRestTemplate.postForObject("http://localhost:8081/api/boards",board,Board.class);
    }
    private Board updateBoard(Board createdBoard){
        String updateUri="http://localhost:8081/api/boards/1";
        testRestTemplate.put(updateUri,createdBoard);
        return testRestTemplate.getForObject(updateUri,Board.class);
    }
}
