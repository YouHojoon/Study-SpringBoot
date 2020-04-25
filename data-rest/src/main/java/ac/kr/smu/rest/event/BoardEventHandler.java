package ac.kr.smu.rest.event;

import ac.kr.smu.rest.domain.Board;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
public class BoardEventHandler {
    @HandleBeforeCreate
    public void beforeCreateBoard(Board board){
        board.setCreatedDateNow();
    }
    @HandleBeforeSave
    public void beforeSaveBoard(Board board){
        board.setUpdatedDateNow();
    }
}
