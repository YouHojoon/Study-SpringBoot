package ac.kr.smu.rest.contoller;

import ac.kr.smu.rest.domain.Board;
import ac.kr.smu.rest.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RepositoryRestController
public class BoardRestController {
    private BoardRepository boardRepository;
    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }
    @GetMapping("/boards")
    public @ResponseBody CollectionModel<Board> simpleBoard(@PageableDefault Pageable pageable){
        Page<Board> boardList=boardRepository.findAll(pageable);
        PageMetadata pageMetadata= new PageMetadata(pageable.getPageSize(),boardList.getNumber(),boardList.getTotalElements());
        PagedModel<Board> resources = new PagedModel<>(boardList.getContent(),pageMetadata);
        resources.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());
        return resources;
    }
    @PostMapping
    public ResponseEntity<?> postBoards(@RequestBody Board board){
        board.setCreatedDateNow();
        boardRepository.save(board);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board){
        Board persistBoard= boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteBoard(@PathVariable("idx")Long idx){
        boardRepository.deleteById(idx);
        return new ResponseEntity<>("{}",HttpStatus.OK);
    }
}
