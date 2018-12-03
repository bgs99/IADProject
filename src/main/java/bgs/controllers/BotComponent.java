package bgs.controllers;

import TAMansfield.bot.SepoBot;
import bgs.repo.*;
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
    private MissionRepository missionRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private InfoRequestRepository infoRequestRepository;
    @Autowired
    private SupportRequestRepository supportRequestRepository;
    @Autowired
    private PortraitRepository portraitRepository;
    @Autowired
    Environment env;

    @PostConstruct
    public void init(){
        if(env.getProperty("TELEGRAM")!=null) {
            ApiContextInitializer.init();
            TelegramBotsApi api = new TelegramBotsApi();
            try {
                api.registerBot(SepoBot.proxyBot(
                        agentRepository,
                        missionRepository,
                        teamRepository,
                        portraitRepository,
                        infoRequestRepository,
                        supportRequestRepository));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
