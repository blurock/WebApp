package info.esblurock.reaction.data.user;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Email;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class UserAccount  extends DatabaseObject {
	
    @Persistent
    String username;
    @Persistent
    String password;
    @Persistent
    String userrole;
    @Persistent
    String email;
    
	public UserAccount() {
		super();
	}
	public UserAccount(String username, String password, String userrole, String emailS) {
		super();
		this.username = username;
		this.password = password;
		this.userrole = userrole;
		this.email = emailS;
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
	public String getEmail() {
		return email;
	}
    
    
}
