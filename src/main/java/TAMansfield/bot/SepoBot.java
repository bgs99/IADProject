package TAMansfield.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

/* 
 //If such darkest times come that "abilities" will have to be discarded -- uncomment this and deal with the API

 import org.telegram.telegrambots.bots.*;
 import org.telegram.telegrambots.meta.api.objects.Update;
*/
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
	
	/*public Ability help() {
	    return null;
	}

	Not needed - there are default commands for that in ability bots
	*/
	
	public Ability getWageInfo() {
		return Ability.builder()
                .name("wage")
                .info("Get your wage")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(ctx -> this.silent.send("0", ctx.chatId()))
                .build();
	}
}
