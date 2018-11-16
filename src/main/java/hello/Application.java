package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repo.TargetRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"repo"})
@EntityScan("model")
public class Application {
    @Autowired
    TargetRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}