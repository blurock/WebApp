package info.esblurock.reaction.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.ui.ReactionFirstView;

public class ReactionFirstActivity extends AbstractActivity implements ReactionFirstView.Presenter {
	// Used to obtain views, eventBus, placeController
	private ClientFactory clientFactory;
	// Name that will be appended to "Hello,"
	private String name;

	public ReactionFirstActivity(ReactionFirstPlace place, ClientFactory clientFactory) {
		this.name = place.getHelloName();
		this.clientFactory = clientFactory;
	}

	/**
	 * Invoked by the ActivityManager to start a new Activity
	 */
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ReactionFirstView reactionFirstView = clientFactory.getReactionFirstView();
		reactionFirstView.setName(name);
		reactionFirstView.setPresenter(this);
		containerWidget.setWidget(reactionFirstView.asWidget());
	}
	   @Override
	    public String mayStop() {
			//return "ReactionFirstActivity: Please hold on. This activity is stopping.";
			return null;
	    }

	
	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
