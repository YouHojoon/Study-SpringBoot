package ac.kr.smu.rest.domain.projection;

import ac.kr.smu.rest.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyName", types ={User.class})
public interface UserOnlyContainName {
    String getName();
}
