package info.esblurock.reaction.server.authorization;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class SetOfAuthorizationLevels {
	static private String linedelimiter = "\n";
	static private HashMap<String,AuthorizationLevel> authorization = null;
	static private HashMap<String,String> levels = null;
	String levelResource = "info/esblurock/reaction/server/authorization/levelterms.txt";
	String AuthResource = "info/esblurock/reaction/server/authorization/authorizationlevels.txt";
	
	
	
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
		URL url = getClass().getClassLoader().getResource(AuthResource);
		String content;
			content = Resources.toString(url,Charsets.UTF_8);
			initAuthorization(content);
	}
	public void initAuthorization(String text) {
		authorization = new HashMap<String,AuthorizationLevel>();
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
	private HashMap<String,String> getLevels() {
		if(levels == null) {
			initLevels();
		}
		return levels;
	}
	private void initLevels() {
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
		
	}
}
