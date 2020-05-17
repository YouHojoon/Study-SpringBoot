package ac.kr.smu.oauth2.repository;

import ac.kr.smu.oauth2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Object> {
    Optional<User> findByUid(Long uid);
    Optional<User> findById(String id);
}
