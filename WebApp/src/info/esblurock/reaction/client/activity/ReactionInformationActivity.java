package info.esblurock.reaction.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import info.esblurock.reaction.client.activity.place.ReactionInformationPlace;
import info.esblurock.reaction.client.ui.ReactionInformationView;

public class ReactionInformationActivity extends AbstractActivity implements ReactionInformationView.Presenter{
	private ClientFactory clientFactory;
	private String name;

	public ReactionInformationActivity(ReactionInformationPlace place, ClientFactory clientFactory) {
		this.name = place.getHelloName();
		this.clientFactory = clientFactory;
	}
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ReactionInformationView reactionInformationView = clientFactory.getReactionInformationView();
		reactionInformationView.setName(name);
		reactionInformationView.setPresenter(this);
		containerWidget.setWidget(reactionInformationView.asWidget());
	}
	   @Override
	    public String mayStop() {
			//return "ReactionInformationActivity: Please hold on. This activity is stopping.";
			return null;
	    }

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
