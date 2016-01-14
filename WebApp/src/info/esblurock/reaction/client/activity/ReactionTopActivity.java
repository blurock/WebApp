package info.esblurock.reaction.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.ui.ReactionTopView;

public class ReactionTopActivity extends AbstractActivity implements ReactionTopView.Presenter {
	
	// Used to obtain views, eventBus, placeController
	// Alternatively, could be injected via GIN
	private ClientFactory clientFactory;
	// Name that will be appended to "Hello,"
	private String name;

	public ReactionTopActivity(ReactionTopPlace place, ClientFactory clientFactory) {
		this.name = place.getHelloName();
		this.clientFactory = clientFactory;
	}

	/**
	 * Invoked by the ActivityManager to start a new Activity
	 */
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ReactionTopView reactionTopView = clientFactory.getReactionTopView();
		reactionTopView.setName(name);
		reactionTopView.setPresenter(this);
		containerWidget.setWidget(reactionTopView.asWidget());
	}

	/**
	 * Ask user before stopping this activity
	 */
	@Override
	public String mayStop() {
		//return "ReactionTopActivity: Please hold on. This activity is stopping.";
		return null;
	}

	/**
	 * Navigate to a new Place in the browser
	 */
	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
	

}
