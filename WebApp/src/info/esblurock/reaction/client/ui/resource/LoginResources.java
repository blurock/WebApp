package info.esblurock.reaction.client.ui.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface LoginResources extends ClientBundle {
	public static final LoginResources INSTANCE = GWT.create(LoginResources.class);
	
	ImageResource OneKey();
	ImageResource SetOfKeys();
	ImageResource SetOfKeysSmall();
	
	
}
