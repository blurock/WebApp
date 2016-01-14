package info.esblurock.reaction.server.event;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class SessionEvent extends DatabaseObject {

    @Persistent
	String userName;
    @Persistent
	String sessionIP;
    @Persistent
	String event;
	
	public SessionEvent() {	
	}

	
	public SessionEvent(String userName, String sessionIP, String event) {
		super();
		this.userName = userName;
		this.sessionIP = sessionIP;
		this.event = event;
	}


	public String getUserName() {
		return userName;
	}

	public String getSessionIP() {
		return sessionIP;
	}

	public String getEvent() {
		return event;
	}	
}
