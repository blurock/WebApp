package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.Iterator;

import info.esblurock.reaction.client.ui.login.LoginService;
import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.user.UserAccount;
import info.esblurock.reaction.server.event.TransactionCount;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

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
				System.out.println("Verifying user");
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

	public String storeUserAccount(UserAccount account) {
		UserAccount userexists = getAccount(account.getUsername());
		String key = null;
		if (userexists == null) {
			userexists = getAccountFromEmail(account.getEmail());
			if (userexists == null) {
				DatabaseObject o = pm.makePersistent(account);
				TransactionCount count = new TransactionCount(account.getUsername());
				pm.makePersistent(count);
				key = o.getKey();
			} else {
				System.out.println("ERROR: email already exists");
			}
		} else {
			System.out.println("ERROR: user name already exists");
		}
		return key;
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
