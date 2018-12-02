package bgs;

import TAMansfield.bot.SepoBot;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"bgs.repo"})
@EntityScan("bgs/model")
public class Application {
    @Autowired
    TargetRepository repository;
    public static void main(String[] args) {
        if(args.length > 0 && args[0].equals("tg")) {
            ApiContextInitializer.init();

            TelegramBotsApi api = new TelegramBotsApi();
            try {
                api.registerBot(SepoBot.proxyBot());
            } catch (TelegramApiRequestException e) {
                System.out.println(e.getMessage());
            }
        }
        SpringApplication.run(Application.class, args);
    }

}