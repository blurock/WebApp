package info.esblurock.reaction.data.user;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class UnverifiedUserAccount extends DatabaseObject {
    @Persistent
    String username;
    @Persistent
    String password;
    @Persistent
    String email;
    
    public UnverifiedUserAccount() {
    	
    }
	public UnverifiedUserAccount(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}

}
