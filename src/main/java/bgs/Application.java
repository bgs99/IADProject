package bgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import bgs.repo.TargetRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"bgs.repo"})
@EntityScan("bgs/model")
public class Application {
    @Autowired
    TargetRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}