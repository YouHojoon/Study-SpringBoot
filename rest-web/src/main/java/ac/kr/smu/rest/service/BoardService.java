package ac.kr.smu.rest.service;

import ac.kr.smu.rest.domain.Board;
import ac.kr.smu.rest.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository){
        this.boardRepository=boardRepository;
    }
    public Page<Board> findBoardList(Pageable pageable){
        pageable= PageRequest.of(pageable.getPageNumber()<=0?0:pageable.getPageNumber()-1,pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }
    public Board findBoardByIdx(Long Idx){
        return boardRepository.findById(Idx).orElse(new Board());
    }
}
