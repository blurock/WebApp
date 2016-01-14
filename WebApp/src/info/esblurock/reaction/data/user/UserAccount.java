package info.esblurock.reaction.data.user;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class UserAccount  extends DatabaseObject {
	
    @Persistent
    String username;
    @Persistent
    String password;
    @Persistent
    String userrole;
    
	public UserAccount() {
		super();
	}
	public UserAccount(String username, String password, String userrole) {
		super();
		this.username = username;
		this.password = password;
		this.userrole = userrole;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getUserrole() {
		return userrole;
	}
    
    
}
