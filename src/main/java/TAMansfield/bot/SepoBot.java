package TAMansfield.bot;

import bgs.repo.AgentRepository;
import bgs.model.*;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

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

        botOptions.setProxyHost("54.38.195.161");
        botOptions.setProxyPort(58770);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
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
                .info("Get your wage")
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
		return Ability.builder().name("showmissionlist").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {
					Agent agent = agentRepository.findByTelegram(ctx.user().getId());
					LinkedList<Mission> mission_list = missionRepository.findUnfinished();
					LinkedList<Integer> ids = new LinkedList<Integer>();
					
					for(Mission mission : mission_list) {
						if(mission.getType().getCharsima() >= portraitRepository.findByAgent(agent).getCharisma()
								&& mission.getType().getLoyalty() >= portraitRepository.findByAgent(agent).getLoyalty()
								&& mission.getType().getAgression() >= portraitRepository.findByAgent(agent).getAgression()	
								) {
							ids.add(mission.getId());
						}
					}
					
                    String msg = new String();
                    for (Integer i : ids) {
                    	msg += i;
                    	msg += "\n";
                    }

                    silent.send(msg, ctx.chatId());
				}).build();
	}

	public Ability showMisssions() {
		return Ability.builder()
				.name("showmisssions")
				.info("Show your missions")
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC)
				.action(ctx -> {
					int id = agentRepository.findByTelegram(ctx.user().getId()).getId();
					LinkedList<Team> teams = teamRepository.findAllByAgent(id);
					LinkedList<Integer> ids = new LinkedList<Integer>();
                    for (Team t : teams) {
                    	if(t.getMission().getStatus() != "Выполнена") ids.add(t.getMission().getId());
                    }

                    /* Forming output and sending it */

                    String msg = new String();
                    for (Integer i : ids) {
                    	msg += i;
                    	msg += "\n";
                    }

                    silent.send(msg, ctx.chatId());
				}).build();
	}
	
	public Ability showMission() {
		return Ability.builder()
			.name("showmission")
			.locality(Locality.USER)
			.input(1)
			.privacy(Privacy.PUBLIC).action(ctx -> {
				try {
				Mission mission = missionRepository.findById(Integer.parseInt(ctx.firstArg()));
				}
				catch(Exception e) {
					silent.send("Error while getting mission from DB", ctx.chatId());
				}
				String msg = String.format("ID: %s\nTYPE: %s\nDESCRIPTION: %s\nSTATUS: %s\nRESPONSIBLE: %s\n", 
						mission.getId(), 
						mission.getType(), 
						mission.getDescription(), 
						mission.getStatus(), 
						mission.getResponsible());
				silent.send(msg, ctx.chatId());
			}).build();
	}
	
	public Ability setStatus() {
		return Ability.builder().name("setstatus").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {
					try {
						Mission mission = missionRepository.findById(Integer.parseInt(ctx.firstArg()));
						}
						catch(Exception e) {
							silent.send("Error while getting mission from DB", ctx.chatId());
						}
					mission.setStatus(ctx.secondArg());
				}).build();
	}
	
	public Ability getMission() {
		return Ability.builder().name("getmission").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {
					try {
						Agent agent = agentRepository.findByTelegram(ctx.user().getId());
						Mission mission = missionRepository.findById(Integer.parseInt(ctx.firstArg()));
						}
						catch(Exception e) {
							silent.send("Error while getting mission from DB", ctx.chatId());
						}
					try {
							if(mission.getType().getCharsima() >= portraitRepository.findByAgent(agent).getCharisma()
								&& mission.getType().getLoyalty() >= portraitRepository.findByAgent(agent).getLoyalty()
								&& mission.getType().getAgression() >= portraitRepository.findByAgent(agent).getAgression()	
								) {
							mission.getTeam.add(agent.getId());
							silent.send("Successfully added", ctx.chatId());
							}
							else {
								silent.send("You're unsuitable for this mission", ctx.chatId());
							}
						}
					catch(Exception e)
					{
						silent.send("Internal error", ctx.chatId());
					}
				}).build();
	}
	
	public Ability requestSupport() {
		return Ability.builder().name("requestsupport").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {


				}).build();
	}
	
	public Ability requestEquipment() {
		return Ability.builder().name("requestequipment").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {


				}).build();
	}
	
	public Ability assign() {
		return Ability.builder().name("assign").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {

					try {
							Agent agent = agentRepository.findById(Integer.parseInt(ctx.firstArg()));
							Mission mission = missionRepository.findById(Integer.parseInt(ctx.secondArg()));
						}
					catch(Exception e) {
							silent.send("Error while accessing DB", ctx.chatId());
						}
					try {
							if(mission.getType().getCharsima() >= portraitRepository.findByAgent(agent).getCharisma()
								&& mission.getType().getLoyalty() >= portraitRepository.findByAgent(agent).getLoyalty()
								&& mission.getType().getAgression() >= portraitRepository.findByAgent(agent).getAgression()	
								) {
							mission.getTeam.add(agent.getId());
							silent.send("Successfully assigned", ctx.chatId());
							}
							else {
								silent.send("He's unsuitable for this mission", ctx.chatId());
							}
						}
					catch(Exception e)
					{
						silent.send("Internal error", ctx.chatId());
					}
				}).build();
	}

	public Ability approveRequest() {
		return Ability.builder().name("approverequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {


				}).build();
	}

	public Ability denyRequest() {
		return Ability.builder().name("denyrequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {


				}).build();
	}
}