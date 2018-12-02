package TAMansfield.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

public class SepoBot extends AbilityBot {

    private final static String BOT_USER_NAME = "SEPO_BOT";
    private final static String BOT_TOKEN = "673753505:AAEdbx1W5Hzz1a-g3q54po2m7Fx-BMDth3g";;


    public static SepoBot proxyBot(){
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("54.38.195.161");
        botOptions.setProxyPort(58770);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return new SepoBot(botOptions);
    }

	private SepoBot(DefaultBotOptions options) {

		super(BOT_TOKEN, BOT_USER_NAME, options);
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
	
	public Ability getWageInfo() {
		Double wage = 0.0d;
		
		/* TODO: GET IT FROM DB */
		
		return Ability.builder()
	    		.name("wageInfo")
	    		.info("get your wage")
	    		.input(0)
	    		.locality(Locality.USER)
	    		.privacy(Privacy.PUBLIC)
	    		.action(ctx -> silent.send(String.format("%d", wage), ctx.chatId()))
	    		.build();
	}
	
	public Ability showMissionList() {
		return Ability.builder().name("showMissionList").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability showMisssions() {
		return Ability.builder().name("showMisssions").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability showMission() {
		return Ability.builder().name("showMission").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability setStatus() {
		return Ability.builder().name("setStatus").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability getMission() {
		return Ability.builder().name("getMission").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability requestSupport() {
		return Ability.builder().name("requestSupport").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability requestEquipment() {
		return Ability.builder().name("requestEquipment").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
	
	public Ability assign() {
		return Ability.builder().name("assign").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability approveRequest() {
		return Ability.builder().name("approveRequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}

	public Ability denyRequest() {
		return Ability.builder().name("denyRequest").locality(Locality.USER)
				.privacy(Privacy.PUBLIC).action(ctx -> {}).build();
	}
}