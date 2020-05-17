package ac.kr.smu.repository;

import ac.kr.smu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Object> {
    Optional<User> findByUid(Long uid);
    Optional<User> findById(String id);
}
