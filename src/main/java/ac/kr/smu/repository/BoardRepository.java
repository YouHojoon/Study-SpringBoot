package ac.kr.smu.repository;

import ac.kr.smu.domain.Board;
import ac.kr.smu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BoardRepository extends JpaRepository<Board,Long> {
    Board findByUser(User user);
}
