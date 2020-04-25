package ac.kr.smu;


import ac.kr.smu.rest.event.BoardEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("ac.kr.smu.rest.repository")
@EntityScan("ac.kr.smu.rest.domain")
@SpringBootApplication
public class DataRestApplication {
    public static void main(String[] args){
        SpringApplication.run(DataRestApplication.class);
    }
    @Bean
    BoardEventHandler boardEventHandler(){
        return new BoardEventHandler();
    }
}
