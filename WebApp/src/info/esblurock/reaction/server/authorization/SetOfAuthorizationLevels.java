package info.esblurock.reaction.server.authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SetOfAuthorizationLevels {
	//static private String linedelimiter = "\n";
	static private HashMap<String,AuthorizationLevel> authorization = null;
	static private HashMap<String,String> levels = null;
	//String levelResource = "info/esblurock/reaction/server/authorization/levelterms.txt";
	//String AuthResource = "info/esblurock/reaction/server/authorization/authorizationlevels.txt";
	
	
	
	public String getTooltip(String level) {
		return getLevels().get(level);
	}
	
	public boolean authorize(String userlevel, String task) throws IOException {
		AuthorizationLevel tasks = getAuthorizations().get(userlevel);
		return tasks.verifiedLevel(task);
	}
	
	public ArrayList<String> getPrivledges(String level) throws IOException {
		AuthorizationLevel tasks = getAuthorizations().get(level);
		return tasks.getPrivledges();
	}
	private HashMap<String,AuthorizationLevel> getAuthorizations() throws IOException {
		if(authorization == null) {
			initAuthorization();
		}
		return authorization;
	}
	public void initAuthorization() throws IOException {
		authorization = new HashMap<String,AuthorizationLevel>();
		String[] lvls1 = {"Login", "Query"};
		AuthorizationLevel alvl1 = new AuthorizationLevel(lvls1);
		authorization.put("Query", alvl1);
		
		String[] lvls2 = {"Login", "Query", "Profile", "Password"};
		AuthorizationLevel alvl2 = new AuthorizationLevel(lvls2);
		authorization.put("StandardUser", alvl2);
		
		String[] lvls3 = {"Login", "Query", "Profile", "Password", "UserDataInput", "UserDataDelete"};
		AuthorizationLevel alvl3 = new AuthorizationLevel(lvls3);
		authorization.put("DataUser", alvl3);
		
		String[] lvls4 = {"Login", "Query", "Profile", "Password", "UserDataInput", "UserDataDelete", "DataInput", "DataDelete"};
		AuthorizationLevel alvl4 = new AuthorizationLevel(lvls4);
		authorization.put("Administrator", alvl4);
		
		String[] lvls5 = {"Login", "Query", "Profile", "Password", "UserDataInput", "UserDataDelete", "DataInput", "DataDelete"};
		AuthorizationLevel alvl5 = new AuthorizationLevel(lvls5);
		authorization.put("SuperUser", alvl5);
		/*
		URL url = getClass().getClassLoader().getResource(AuthResource);
		String content = Resources.toString(url,Charsets.UTF_8);
		initAuthorization(content);
		*/
	}
	/*	
	 * 	public void initAuthorization(String text) {
		
		
	
		StringTokenizer tok = new StringTokenizer(text, linedelimiter);
		while(tok.hasMoreTokens()) {
			String level = tok.nextToken();
			if(tok.hasMoreTokens()) {
				String levels = tok.nextToken();
				AuthorizationLevel lvls = new AuthorizationLevel(levels);
				authorization.put(level,lvls);
			}
		}
		
	}
	*/
	private HashMap<String,String> getLevels() {
		if(levels == null) {
			initLevels();
		}
		return levels;
	}
	private void initLevels() {
		levels = new HashMap<String,String>();
		levels.put("Login", "Ability to login");
		levels.put("Query", "Ability to execute queries");
		levels.put("Profile", "Able to change own profile");
		levels.put("Password", "Able to change own password");
		levels.put("UserDataInput", "Able to input data under users name");
		levels.put("UserDataDelete", "Able to delete data under users name");
		levels.put("DataInput", "Able to input data under other users name");
		levels.put("DataDelete", "Able to delete data under other users name");
		levels.put("ALL", "Able to perform all operations");		
/*		
		URL url = getClass().getClassLoader().getResource(levelResource);
		String content;
		try {
			content = Resources.toString(url, Charsets.UTF_8);
			levels = new HashMap<String,String>();
			StringTokenizer tok = new StringTokenizer(content, linedelimiter);
			while(tok.hasMoreTokens()) {
				String level = tok.nextToken();
				if(tok.hasMoreTokens()) {
					String tooltip = tok.nextToken();
					levels.put(level,tooltip);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
