package info.esblurock.reaction.client.ui.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface ReactionTopViewResources extends ClientBundle {
	public static final ReactionTopViewResources INSTANCE = GWT.create(ReactionTopViewResources.class);
	
	@Source("item1.txt")
	  public TextResource exampleItem1();
	
	@Source("item2.txt")
	public TextResource exampleItem2();
	
	@Source("item3.txt")
	public TextResource exampleItem3();
	
	@Source("item4.txt")
	public TextResource exampleItem4();
	
	@Source("firstdescription.txt")
	public TextResource firstdescription();
	
	@Source("chemconnectdescription.txt")
	public TextResource chemconnectdescription();

	@Source("usersearching.txt")
	public TextResource usersearching();
	
	@Source("datarelations.txt")
	public TextResource datarelations();
	
	@Source("topsummary.txt")
	public TextResource topsummary();
	

	
}
