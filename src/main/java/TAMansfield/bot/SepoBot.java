package TAMansfield.bot;

import bgs.repo.AgentRepository;
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

    public static SepoBot proxyBot(AgentRepository agentRepository){

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("54.38.195.161");
        botOptions.setProxyPort(58770);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return new SepoBot(botOptions, agentRepository);
    }

	private SepoBot(DefaultBotOptions options, AgentRepository agentRepository) {
		super(BOT_TOKEN, BOT_USER_NAME, options);
		this.agentRepository = agentRepository;
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

	/* -------------------- 
	 * These abilities rely on SepoDBHandler
	 * which provides DB connection 
	 * and complies with DBContext interface.
	 * -------------------- 
	 * As the SepoDBHandler is not yet functional
	 * those are stubs returning null.
	 * We're working on it.
	 * -------------------- */

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
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability showMisssions() {
		return Ability.builder().name("showmisssions").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability showMission() {
		return Ability.builder().name("showmission").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability setStatus() {
		return Ability.builder().name("setstatus").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability getMission() {
		return Ability.builder().name("getmission").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability requestSupport() {
		return Ability.builder().name("requestsupport").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability requestEquipment() {
		return Ability.builder().name("requestequipment").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability assign() {
		return Ability.builder().name("assign").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability approveRequest() {
		return Ability.builder().name("approverequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability denyRequest() {
		return Ability.builder().name("denyrequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
}