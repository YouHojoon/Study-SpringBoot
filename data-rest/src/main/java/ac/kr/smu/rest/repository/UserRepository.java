package ac.kr.smu.rest.repository;


import ac.kr.smu.rest.domain.User;
import ac.kr.smu.rest.domain.projection.UserOnlyContainName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long>  {

}
