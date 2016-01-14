package info.esblurock.reaction.server;

import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class StringToKeyConversion {
	
	String delimiterS = ".";
	
	public String convertKeyToString(Key key) {
		long id = key.getId();
		Long idL = new Long(id);
		String idS = idL.toString();
		String kind = key.getKind();
		String keyS = kind + delimiterS + idS;
		return keyS;
	}
	
	public Key convertStringToKey(String keyS) {
		StringTokenizer tok = new StringTokenizer(keyS, delimiterS);
		Key id = null;
		if(tok.countTokens() == 2) {
			String kindS = tok.nextToken();
			String idS = tok.nextToken();
			Long idL = Long.getLong(keyS);
			id = KeyFactory.createKey(kindS, idL.longValue());
		}
		return id;
	}
}
