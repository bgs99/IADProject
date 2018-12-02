package bgs.controllers;

import TAMansfield.bot.SepoBot;
import bgs.repo.AgentRepository;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
public class BotComponent {
    @Autowired
    TargetRepository repository;
    @Autowired
    AgentRepository agentRepository;

    @PostConstruct
    public void init(){
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(SepoBot.proxyBot(agentRepository));
        } catch (TelegramApiRequestException e) {
            System.out.println(e.getMessage());
        }
    }
}
