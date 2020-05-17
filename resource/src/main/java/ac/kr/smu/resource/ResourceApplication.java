package ac.kr.smu.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ac.kr.smu.oauth2.repository")
@EntityScan("ac.kr.smu.oauth2.domain")
public class ResourceApplication {
    public static void main(String[] args){
        SpringApplication.run(ResourceApplication.class, args);
    }
}
