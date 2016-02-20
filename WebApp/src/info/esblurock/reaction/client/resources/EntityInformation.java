package info.esblurock.reaction.client.resources;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/Users/edwardblurock/git/WebApp/src/info/esblurock/reaction/client/resources/EntityInformation.properties'.
 */
public interface EntityInformation extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "line".
   * 
   * @return translated "line"
   */
  @DefaultMessage("line")
  @Key("inputfileline")
  String inputfileline();

  /**
   * Translated "count".
   * 
   * @return translated "count"
   */
  @DefaultMessage("count")
  @Key("inputfilelinecount")
  String inputfilelinecount();

  /**
   * Translated "inputfileupload".
   * 
   * @return translated "inputfileupload"
   */
  @DefaultMessage("inputfileupload")
  @Key("inputfileupload")
  String inputfileupload();

  /**
   * Translated "Welcome.  The current time is {0}.".
   * 
   * @return translated "Welcome.  The current time is {0}."
   */
  @DefaultMessage("Welcome.  The current time is {0}.")
  @Key("welcome")
  String welcome(String arg0);
}
