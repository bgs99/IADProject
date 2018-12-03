package bgs.controllers;

import TAMansfield.bot.SepoBot;
import bgs.repo.AgentRepository;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.PostConstruct;

@Component
public class BotComponent {
    @Autowired
    TargetRepository repository;
    @Autowired
    AgentRepository agentRepository;
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
        }
    }
}
