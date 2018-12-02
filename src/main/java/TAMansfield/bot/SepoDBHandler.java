package iad_bot;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.Var;

public class SepoDBHandler implements DBContext {

	/*
	Database handler for replies in the bot.
	The library relies on the DBContext interface while providing its default implemenatation
	Custom implementation deemed necessary for our DB
	*/

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object backup() {
		/* No need for a backup */
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> List<T> getList(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> getMap(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Set<T> getSet(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Var<T> getVar(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String info(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean recover(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String summary() {
		// TODO Auto-generated method stub
		return null;
	}

}
