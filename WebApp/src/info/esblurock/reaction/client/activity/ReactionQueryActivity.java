package info.esblurock.reaction.client.activity;

import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.ui.ReactionQueryView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import gwt.material.design.client.ui.MaterialToast;

public class ReactionQueryActivity  extends AbstractActivity implements ReactionQueryView.Presenter {

	// Used to obtain views, eventBus, placeController
	private ClientFactory clientFactory;
	// Name that will be appended to "Hello,"
	private String name;

	public ReactionQueryActivity(ReactionQueryPlace place, ClientFactory clientFactory) {
		this.name = place.getHelloName();
		this.clientFactory = clientFactory;
	}

	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		Window.alert("ReactionQueryActivity.start");
		Window.alert("ContainerWidget:  " + containerWidget.toString());
		ReactionQueryView reactionQueryView = clientFactory.getReactionQueryView();
		Window.alert("ReactionQueryView:  " + containerWidget.toString());		
		reactionQueryView.setName(name);
		reactionQueryView.setPresenter(this);
		containerWidget.setWidget(reactionQueryView.asWidget());
		Window.alert("ReactionQueryActivity.start finished");
		
	}
	   @Override
	    public String mayStop() {
			//return "ReactionQueryActivity: Please hold on. This activity is stopping.";
		   return null;
	    }

	@Override
	public void goTo(Place place) {
		MaterialToast.fireToast("ReactionQueryActivity.goTo: " + place.toString());
		clientFactory.getPlaceController().goTo(place);
	}

}
