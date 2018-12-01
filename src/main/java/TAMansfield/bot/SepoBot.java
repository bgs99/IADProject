package iad_bot;
import org.telegram.abilitybots.api.bot.*;
import org.telegram.abilitybots.api.objects.*;

/* 
 //If such darkest times come that "abilities" will have to be discarded -- uncomment this and deal with the API

 import org.telegram.telegrambots.bots.*;
 import org.telegram.telegrambots.meta.api.objects.Update;
*/
public class SepoBot extends AbilityBot {

	protected SepoBot(String botToken, String botUsername) {
		super(botToken, botUsername);
	}

	@Override
	public String getBotUsername() {
		return "SEPO_BOT";
	}

	@Override
	public String getBotToken() {
		return "673753505:AAEdbx1W5Hzz1a-g3q54po2m7Fx-BMDth3g";
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
	
	public Ability help() {
	    return null;
	}
	
	public Ability getWageInfo() {
		return null;
	}
}
