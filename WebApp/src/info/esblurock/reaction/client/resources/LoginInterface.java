package info.esblurock.reaction.client.resources;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'/Users/edwardblurock/.git/WebApp/WebApp/src/info/esblurock/reaction/client/resources/LoginInterface.properties'.
 */
public interface LoginInterface extends com.google.gwt.i18n.client.Constants {
  
  /**
   * Translated "LoginInterface".
   * 
   * @return translated "LoginInterface"
   */
  @DefaultStringValue("LoginInterface")
  @Key("ClassName")
  String ClassName();

  /**
   * Translated "User Profile".
   * 
   * @return translated "User Profile"
   */
  @DefaultStringValue("User Profile")
  @Key("LoginProfile")
  String LoginProfile();

  /**
   * Translated "Activate Account".
   * 
   * @return translated "Activate Account"
   */
  @DefaultStringValue("Activate Account")
  @Key("LoginValidationActivate")
  String LoginValidationActivate();

  /**
   * Translated "Congratulations: Your email has been validated. Press the button to activate the account".
   * 
   * @return translated "Congratulations: Your email has been validated. Press the button to activate the account"
   */
  @DefaultStringValue("Congratulations: Your email has been validated. Press the button to activate the account")
  @Key("LoginValidationText")
  String LoginValidationText();

  /**
   * Translated "Congratulations".
   * 
   * @return translated "Congratulations"
   */
  @DefaultStringValue("Congratulations")
  @Key("LoginValidationTitle")
  String LoginValidationTitle();
}
