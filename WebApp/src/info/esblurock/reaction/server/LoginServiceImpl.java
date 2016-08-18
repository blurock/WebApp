package info.esblurock.reaction.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import info.esblurock.reaction.client.ui.login.LoginService;
import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.user.UnverifiedUserAccount;
import info.esblurock.reaction.data.user.UserAccount;
import info.esblurock.reaction.server.datastore.contact.GeocodingLatituteAndLongitude;
import info.esblurock.reaction.server.event.TransactionCount;
import info.esblurock.reaction.server.mail.SendMail;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class LoginServiceImpl extends ServerBase implements LoginService {
	private static final long serialVersionUID = 4456105400553118785L;
	
	String from = "edward.blurock@gmail.com";

	PersistenceManager pm = PMF.get().getPersistenceManager();

	public static String login = "Login";

	String admin = "Administration";
	String adminpass = "laguna";
	String level = "SuperUser";

	@Override
	public UserDTO loginServer(String name, String password) throws IOException {
		String msg1 = "loginServer: " + name + " Password: " + password;
		System.out.println(msg1);
		ContextAndSessionUtilities util = getUtilities();
		String passwd = null;
		String lvl = null;
		if (admin.equals(name)) {	
			System.out.println("Login: System Administrator");
			passwd = adminpass;
			lvl = level;
			try {
				TransactionCount count = pm.getObjectById(TransactionCount.class, admin);
			} catch (Exception ex) {
				TransactionCount count = new TransactionCount(admin);
				pm.makePersistent(count);
			}
		} else {
			System.out.println("Login: Normal user: " + name);
			
			UserAccount account = getAccount(name);
			if (account != null) {
				passwd = account.getPassword();
				lvl = account.getUserrole();
			} else {
				System.out.println("User not found");
				throw new IOException("User not found");
			}
		}

		UserDTO user = null;
		if (passwd != null) {
			if (password.equals(passwd)) {
				System.out.println("Password matches");
				String sessionid = util.getId();
				System.out.println("sessionid= " + sessionid);
				String ip = getThreadLocalRequest().getRemoteAddr();
				System.out.println("IP=" + ip);
				String host = getThreadLocalRequest().getRemoteHost();
				System.out.println("Host=" + host);
				user = new UserDTO(name, sessionid, ip, host, lvl);
				user.setPrivledges(getPrivledges(lvl));
				util.setUserInfo(user);
				System.out.println("Verifying user: " + login);
				verify(login, login);
				System.out.println("Verified user");
			} else {
				throw new IOException("name mismatch; " + name);
			}
		} else {
			throw new IOException("name mismatch; " + name);
		}
		return user;
	}
	
	private UnverifiedUserAccount getUnverifiedAccount(String username) throws IOException {
		UnverifiedUserAccount unverified = null;
		List<DatabaseObject> lst = QueryBase.getDatabaseObjectsFromSingleProperty(UnverifiedUserAccount.class.getName(),"username",username);
		if(lst.size() > 0) {
			unverified = (UnverifiedUserAccount) lst.get(0);
		} else {
			throw new IOException("Account not available to be activated for " + username);
		}
		System.out.println("loginVerification: " + unverified);
		return unverified;
	}
	@Override
	public String loginVerification(String username, String key) throws IOException {
		System.out.println("loginVerification: " + username);
		System.out.println("loginVerification: " + key);
		//UnverifiedUserAccount unverified = (UnverifiedUserAccount) QueryBase.getObjectById(UnverifiedUserAccount.class, key);
		List<DatabaseObject> lst = QueryBase.getDatabaseObjectsFromSingleProperty(UnverifiedUserAccount.class.getName(),"username",username);
		UnverifiedUserAccount unverified = getUnverifiedAccount(username);
		System.out.println("loginVerification: " + unverified);
		String email = unverified.getEmail();
		if(unverified.getUsername().equals(username)) {
			String password = unverified.getPassword();
			Date creation = unverified.getCreationDate();
			String userrole = "StandardUser";
			
			UserAccount account = new UserAccount(username,password,userrole,email);
			WriteObjectTransactionToDatabase.writeObjectWithoutTransaction(account);

			String subject = "Welcome to MolConnect";
			String msg = "Your account has been verified<br>"
					+ "This account is under a limited usage agreement <br>"
					+ "Have fun.<br>"
					+ "<br>"
					+ "Updates and instructions will be posted on the website<br>"
					+ "<br>"
					+ "If you have any questions, don't hesitate to email me<br>"
					+ "Regards<br>"
					+ "Edward Blurock<br>";
			SendMail send = new SendMail();
			send.sendMail(email, from, subject, msg);
			
		} else {
			throw new IOException("'" + username + "' does not match authorization key");
		}
		return email;
	}

	@Override
	public String firstLoginToServer(String username)  throws IOException{
		UnverifiedUserAccount unverified = getUnverifiedAccount(username);
		loginServer(unverified.getUsername(), unverified.getPassword());
		QueryBase.deleteFromIdentificationCode(UnverifiedUserAccount.class,"username",username);
		return username;
	}
	@Override
	public UserDTO loginFromSessionServer() {
		ContextAndSessionUtilities util = getUtilities();
		UserDTO user = util.getUserInfoFromContext();
		return user;
	}

	@Override
	public void logout() {
		ContextAndSessionUtilities util = getUtilities();
		util.removeUser();
	}

	@Override
	public Boolean changePassword(String name, String newPassword) {
		return false;
		// change password logic
	}

	public String storeUserAccount(UserAccount account)  {
		UserAccount userexists = getAccount(account.getUsername());
		String useremail = null;
		if (userexists == null) {
			userexists = getAccountFromEmail(account.getEmail());
			if (userexists == null) {

				UnverifiedUserAccount unverified = 
						new UnverifiedUserAccount(account.getUsername(), 
								account.getPassword(),
								account.getEmail());
				DatabaseObject toverify = pm.makePersistent(unverified);
				
				String msg = "http://9-dot-blurock-reaction.appspot.com/WebApp.html?id=2#ReactionLoginValidation:bbb";
				//String host = "http://127.0.0.1:8080/";
				String host = "http://blurock-reaction.appspot.com/";
				String webappS = "WebApp.html";
				String page = "ReactionLoginValidationPlace:validate";
				String charset = StandardCharsets.UTF_8.name();

				String id;
				try {
					id = "id=" + URLEncoder.encode(toverify.getKey(),charset);
					String name = "name=" + URLEncoder.encode(account.getUsername(),charset);
					String vlink = host + webappS + "?" + id +"&" + name + "#" + page;
					
					String message = "Thank you for registering for an account in MolConnect. "
					+ "<br>To activate your account, just follow the following link:"
					+ "<br><a href=\"" + vlink + "\">MolConnect email Validation</a>"
					+ "<br><br>Regards"
					+ "<br><b>MolConnect<b> at"
					+ "<br><it>Blurock Consulting AB<it>";
					
					System.out.println("Send Message: (" + account.getEmail() + ")\n" + message);
					SendMail mail = new SendMail();
					String to = "edward.blurock@esblurock.info";
					String subject = "MolConnect: account email validation";
					mail.sendMail(to, from, subject, message);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				DatabaseObject o = pm.makePersistent(account);
				TransactionCount count = new TransactionCount(account.getUsername());
				pm.makePersistent(count);
				useremail = account.getEmail();
			} else {
				System.out.println("ERROR: email already exists");
			}
		} else {
			System.out.println("ERROR: user name already exists");
		}
		return useremail;
	}

	public UserAccount getAccount(String username) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserAccount account = null;
		Filter userfilter = new FilterPredicate("username", FilterOperator.EQUAL, username);
		Query q = new Query("UserAccount").setFilter(userfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			Entity result = iter.next();
			Key key = result.getKey();
			account = pm.getObjectById(UserAccount.class, key);
		}
		return account;
	}

	public UserAccount getAccountFromEmail(String email) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserAccount account = null;
		Filter userfilter = new FilterPredicate("email", FilterOperator.EQUAL, email);
		Query q = new Query("UserAccount").setFilter(userfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			Entity result = iter.next();
			Key key = result.getKey();
			account = pm.getObjectById(UserAccount.class, key);
		}
		return account;
	}

	public String removeUser(String key) {
		String ans = "SUCCESS";
		try {
			UserAccount account = pm.getObjectById(UserAccount.class, key);

			pm.deletePersistent(account);
		} catch (Exception ex) {
			ans = ex.toString();
		}
		return ans;
	}

}
