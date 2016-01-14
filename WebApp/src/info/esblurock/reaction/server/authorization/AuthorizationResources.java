package info.esblurock.reaction.server.authorization;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface AuthorizationResources extends ClientBundle {
	
	public static AuthorizationResources INSTANCE = GWT.create(AuthorizationResources.class);
	
	@Source("authorizationlevels.txt")
	  public TextResource authorizationLevels();
	@Source("levelterms.txt")
	  public TextResource levelTerms();

}
