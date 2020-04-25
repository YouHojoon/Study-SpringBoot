package ac.kr.smu.rest.domain.projection;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class CustomizedRestMvcConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.getProjectionConfiguration().addProjection(UserOnlyContainName.class);
    }
}
