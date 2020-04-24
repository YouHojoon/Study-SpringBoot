package ac.kr.smu.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ac.kr.smu.rest.repository")
@EntityScan("ac.kr.smu.rest.domain")
@SpringBootApplication
public class RestWebApplication {
    public static void main(String[] args){
        SpringApplication.run(RestWebApplication.class);
    }
}
