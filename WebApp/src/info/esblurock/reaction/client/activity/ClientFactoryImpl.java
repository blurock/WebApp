package info.esblurock.reaction.client.activity;

import info.esblurock.reaction.client.ui.ReactionFirstImpl;
import info.esblurock.reaction.client.ui.ReactionFirstView;
import info.esblurock.reaction.client.ui.ReactionLoginValidationImpl;
import info.esblurock.reaction.client.ui.ReactionLoginValidationView;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionQueryView;
import info.esblurock.reaction.client.ui.ReactionTopView;
import info.esblurock.reaction.client.ui.ReactionTopImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;

public class ClientFactoryImpl implements ClientFactory
{
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final ReactionTopView reactionTopView = new ReactionTopImpl();
	private static final ReactionFirstView reactionFirstView = new ReactionFirstImpl();
	private static final ReactionQueryView reactionQueryView = new ReactionQueryImpl();
	private static final ReactionLoginValidationView reactionLoginValidationView = new ReactionLoginValidationImpl();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}
	@Override
	public ReactionTopView getReactionTopView() {
		return reactionTopView;
	}
	@Override
	public ReactionFirstView getReactionFirstView() {
		return reactionFirstView;
	}
	@Override
	public ReactionQueryView getReactionQueryView() {
		return reactionQueryView;
	}
	@Override
	public ReactionLoginValidationView getReactionLoginValidationView() {
		return reactionLoginValidationView;
	}


}
