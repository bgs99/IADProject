package TAMansfield.bot;

import bgs.model.Agent;
import bgs.model.Mission;
import bgs.model.SupportRequest;
import bgs.model.Team;
import bgs.repo.*;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class SepoBot extends AbilityBot {

    private final static String BOT_USER_NAME = "SEPO_BOT";
    private final static String BOT_TOKEN = "673753505:AAEdbx1W5Hzz1a-g3q54po2m7Fx-BMDth3g";;
	private AgentRepository agentRepository;
	private MissionRepository missionRepository;
	private TeamRepository teamRepository;
	private InfoRequestRepository infoRequestRepository;
	private SupportRequestRepository supportRequestRepository;
	private PortraitRepository portraitRepository;

    public static SepoBot proxyBot(AgentRepository agentRepository, 
    		MissionRepository missionRepository, 
    		TeamRepository teamRepository,
    		PortraitRepository portraitRepository,
   			InfoRequestRepository infoRequestRepository,
			SupportRequestRepository supportRequestRepository){

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("51.254.50.239");
        botOptions.setProxyPort(3128);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
        return new SepoBot(botOptions, agentRepository, missionRepository, teamRepository, portraitRepository, infoRequestRepository, supportRequestRepository);
    }

	private SepoBot(DefaultBotOptions options, 
			AgentRepository agentRepository, 
    		MissionRepository missionRepository, 
    		TeamRepository teamRepository,
    		PortraitRepository portraitRepository,
   			InfoRequestRepository infoRequestRepository,
			SupportRequestRepository supportRequestRepository) {
		super(BOT_TOKEN, BOT_USER_NAME, options);
		this.agentRepository = agentRepository;
		this.missionRepository = missionRepository;
		this.teamRepository = teamRepository;
		this.portraitRepository = portraitRepository;
		this.infoRequestRepository = infoRequestRepository;
		this.supportRequestRepository = supportRequestRepository;
	}
	@Override
	public String getBotUsername() {
		return BOT_USER_NAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}

	@Override
	public int creatorId() {
		/* replace this with actual id */
		return 0;
	}

	/* -------------------- */
	/*  ABILITIES GO BELOW  */
	/*    TREAD LIGHTLY     */
	/* -------------------- */


	public Ability getId() {
        return Ability.builder()
                .name("getid")
                .info("Get your telegram id")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(ctx -> {
                    int id = ctx.user().getId();
                    silent.send(String.format("%d", id), ctx.chatId());
                })
                .build();
    }

	public Ability getWageInfo() {
        return Ability.builder()
                .name("wageinfo")
                .info("Show your current wage")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(ctx -> {
                    int wage = agentRepository.findByTelegram(ctx.user().getId()).getPayment();
                    silent.send(String.format("%d", wage), ctx.chatId());
                })
                .build();
	}
	
	
	public Ability showMissionList() {
		return Ability.builder().name("showmissionlist")
				.info("Shows all available missions as \"ID - TYPE\" that you can assign yourself on.")
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {
					Agent agent = agentRepository.findByTelegram(ctx.user().getId());
					if(getCurrentMission(ctx) != null){
                        silent.send("Already on a mission, see /showcurrent", ctx.chatId());
                        return;
                    }
					List<Mission> mission_list = missionRepository.findAcceptable(agent);

                    SendMessage sm = new SendMessage();

                    ReplyKeyboardMarkup rk = new ReplyKeyboardMarkup();
                    List<KeyboardRow> bs = new ArrayList<>();
                    StringBuilder msg = new StringBuilder();

                    for (Mission i : mission_list) {
                        KeyboardRow kr = new KeyboardRow();
                        KeyboardButton kb = new KeyboardButton();
                        kb.setText(String.format("/showmission %d", i.getId()));
                        kr.add(kb);
                        bs.add(kr);
                    	msg.append(String.format("\n/showmission %d", i.getId()));
                    }
                    if(msg.toString().isEmpty()){
                        silent.send("No missions available", ctx.chatId());
                        return;
                    }
                    rk.setKeyboard(bs);
                    rk.setOneTimeKeyboard(true);
                    rk.setResizeKeyboard(true);
                    sm.setReplyMarkup(rk);
                    sm.setText(String.format("Here are all missions available for you:\n%s", msg.toString()));
                    sm.setChatId(ctx.chatId());
                    try {
                        sender.execute(sm);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }).build();
	}

	public Mission getCurrentMission(MessageContext ctx){
        Mission m = null;
	    Agent a = agentRepository.findByTelegram(ctx.user().getId());
        m = missionRepository.findActiveByResponsible(a).orElse(null);
        if(m == null){
            Team t = teamRepository.findActiveByAgent(a).orElse(null);
            if(t == null){
                return null;
            }
            m = t.getMission();
        }
        return m;
    }


	public Ability showMissions() {
		return Ability.builder()
				.name("showcurrent")
				.info("Show mission you're assigned to.")
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC)
				.action(ctx -> {

                    Mission m = getCurrentMission(ctx);
                    if(m != null)
                        silent.send(showMissionDetails(m), ctx.chatId());
					else
                        silent.send("You are not assigned to any mission currently", ctx.chatId());
				}).build();
	}

	public String showMissionDetails(Mission mission){
        return String.format("ID: %s\nTYPE: %s\nDESCRIPTION: %s\nSTATUS: %s\nRESPONSIBLE: %s\n",
                mission.getId(),
                mission.getType().getName(),
                mission.getDescription(),
                mission.getStatus(),
                mission.getResponsible().getName());
    }

	public Ability showMission() {
		return Ability.builder()
                .name("showmission")
                .info("Shows information about mission")
                .locality(Locality.USER)
                .input(1)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Mission mission = null;
                    try {
                        mission = missionRepository.findById(Integer.parseInt(ctx.firstArg()));
                    } catch(Exception e) {
                        silent.send("Error while getting mission from DB", ctx.chatId());
                        return;
                    }




                    SendMessage sm = new SendMessage();

                    ReplyKeyboardMarkup rk = new ReplyKeyboardMarkup();
                    List<KeyboardRow> bs = new ArrayList<>();
                    KeyboardRow kr = new KeyboardRow();

                    KeyboardButton kb = new KeyboardButton();
                    kb.setText(String.format("/getmission %d", mission.getId()));
                    if(getCurrentMission(ctx) == null){
                        kr.add(kb);
                    }
                    bs.add(kr);
                    rk.setKeyboard(bs);
                    rk.setOneTimeKeyboard(true);
                    rk.setResizeKeyboard(true);
                    sm.setReplyMarkup(rk);
                    sm.setText(showMissionDetails(mission));
                    sm.setChatId(ctx.chatId());

                    try {
                        sender.execute(sm);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

			    }).build();
	}
	
	public Ability setStatus() {
		return Ability.builder()
                .name("setstatus")
                .locality(Locality.USER)
                .input(1)
				.privacy(Privacy.PUBLIC).action(ctx -> {
                    Mission mission = getCurrentMission(ctx);
                    if(mission == null){
                        silent.send("Not on a mission", ctx.chatId());
                    }
					mission.setStatus(ctx.firstArg());
					missionRepository.save(mission);
					silent.send(String.format("Status changed to %s", ctx.firstArg()), ctx.chatId());
				}).build();
	}
	
	public Ability getMission() {
		return Ability.builder().name("getmission")
				.info("Assign yourself for the mission")
				.locality(Locality.USER)
				.input(1)
				.privacy(Privacy.PUBLIC).action(ctx -> {
				    Agent agent = null;
				    Mission mission = null;
                    if(getCurrentMission(ctx) != null){
                        silent.send("Already on a mission, see /showcurrent", ctx.chatId());
                        return;
                    }
					try {
						agent = agentRepository.findByTelegram(ctx.user().getId());
						mission = missionRepository.findById(Integer.parseInt(ctx.firstArg()));
                    }
                    catch(Exception e) {
                        silent.send("Error while getting information from DB", ctx.chatId());
                    }

                    if(mission == null || agent == null)
                        return;
					try {
                        if(mission.getType().getCharisma() <= portraitRepository.findByAgent(agent).getCharisma()
                            && mission.getType().getLoyalty() <= portraitRepository.findByAgent(agent).getLoyalty()
                            && mission.getType().getAggression() <= portraitRepository.findByAgent(agent).getAggression()

                            ) {
                            Team t = new Team(agent, mission, null, null, null);
                            teamRepository.save(t);
                            silent.send("Successfully added", ctx.chatId());
                        }
                        else {
                            silent.send("You're unsuitable for this mission", ctx.chatId());
                        }
                    } catch(Exception e){
						silent.send("Internal error", ctx.chatId());
						silent.send(e.getMessage(), ctx.chatId());
						throw e;
					}
				}).build();
	}
	
	public Ability requestSupport() {
		return Ability.builder()
                .name("requestsupport")
                .info("Request soldiers for your current mission")
                .locality(Locality.USER)
				.input(1)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
				    Mission m = getCurrentMission(ctx);
				    if(m == null){
				        silent.send("Not on a mission", ctx.chatId());
				        return;
                    }

                    SupportRequest r = new SupportRequest(m, null, Integer.parseInt(ctx.secondArg()), null, null);
                    supportRequestRepository.save(r);
					silent.send(String.format("Requested %s soldiers for mission %s", ctx.firstArg(), m.getId()), ctx.chatId());
				}).build();
	}
	
	/*public Ability assign() {
		return Ability.builder().name("assign").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {

                    Agent agent = null;
                    Mission mission = null;
					try {
                        agent = agentRepository.findById(Integer.parseInt(ctx.firstArg()));
                        mission = missionRepository.findById(Integer.parseInt(ctx.secondArg()));
                    }
					catch(Exception e) {
                        silent.send("Error while accessing DB", ctx.chatId());
                    }
					try {
                        if(mission.getType().getCharisma() >= portraitRepository.findByAgent(agent).getCharisma()
                            && mission.getType().getLoyalty() >= portraitRepository.findByAgent(agent).getLoyalty()
                            && mission.getType().getAggression() >= portraitRepository.findByAgent(agent).getAggression()
                            ) {

                            Team t = new Team(agent, mission, null, null, null);
							teamRepository.save(t);
							silent.send("Successfully assigned", ctx.chatId());
                        }
                        else {
                            silent.send("He's unsuitable for this mission", ctx.chatId());
                        }
                    } catch(Exception e) {
						silent.send("Internal error", ctx.chatId());
					}
				}).build();
	}*/
}