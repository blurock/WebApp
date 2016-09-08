package info.esblurock.reaction.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.client.activity.place.ReactionLoginValidationPlace;
import info.esblurock.reaction.client.ui.ReactionLoginValidationView;

public class ReactionLoginValidationActivity extends AbstractActivity implements ReactionLoginValidationView.Presenter {
	// Used to obtain views, eventBus, placeController
	private ClientFactory clientFactory;
	// Name that will be appended to "Hello,"
	private String name;

	public ReactionLoginValidationActivity(ReactionLoginValidationPlace place, ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
		this.name = place.getHelloName();
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ReactionLoginValidationView reactionLoginValidationView = clientFactory.getReactionLoginValidationView();
		reactionLoginValidationView.setName(name);
		reactionLoginValidationView.setPresenter(this);
		containerWidget.setWidget(reactionLoginValidationView.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
