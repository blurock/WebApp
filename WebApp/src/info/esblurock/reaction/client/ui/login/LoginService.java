package info.esblurock.reaction.client.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.user.UserAccount;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

    /* Utility class for simplifying access to the instance of async service.
    */
   public static class Util
   {
       private static LoginServiceAsync instance;

       public static LoginServiceAsync getInstance()
       {
           if (instance == null)
           {
               instance = GWT.create(LoginService.class);
           }
           return instance;
       }
   }

   public UserDTO loginServer(String name, String password) throws Exception;

   public UserDTO loginFromSessionServer();
    
   public Boolean changePassword(String name, String newPassword);

   public void logout();
   
   public String storeUserAccount(UserAccount account);

   public String removeUser(String key);
   
   public UserAccount getAccount(String key);
}
