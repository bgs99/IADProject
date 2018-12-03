package bgs.controllers;

import TAMansfield.bot.SepoBot;
import bgs.repo.AgentRepository;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
=======
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb

import javax.annotation.PostConstruct;

@Component
public class BotComponent {
    @Autowired
    TargetRepository repository;
    @Autowired
    AgentRepository agentRepository;
<<<<<<< HEAD

    @PostConstruct
    public void init(){
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(SepoBot.proxyBot(agentRepository));
        } catch (TelegramApiRequestException e) {
            System.out.println(e.getMessage());
=======
    @Autowired
    Environment env;

    @PostConstruct
    public void init(){
        if(env.getProperty("TELEGRAM")!=null) {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            try {
                api.registerBot(SepoBot.proxyBot(agentRepository));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
        }
    }
}
