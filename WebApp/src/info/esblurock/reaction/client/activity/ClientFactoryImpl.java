package info.esblurock.reaction.client.activity;

import info.esblurock.reaction.client.ui.ReactionFirstImpl;
import info.esblurock.reaction.client.ui.ReactionFirstView;
import info.esblurock.reaction.client.ui.ReactionInformationImpl;
import info.esblurock.reaction.client.ui.ReactionInformationView;
import info.esblurock.reaction.client.ui.ReactionLoginValidationImpl;
import info.esblurock.reaction.client.ui.ReactionLoginValidationView;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionQueryView;
import info.esblurock.reaction.client.ui.ReactionTopView;
import info.esblurock.reaction.client.ui.ReactionTopImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final ReactionTopView reactionTopView = new ReactionTopImpl();
	private final ReactionFirstView reactionFirstView = new ReactionFirstImpl();
	private final ReactionQueryView reactionQueryView = new ReactionQueryImpl();
	private final ReactionLoginValidationView reactionLoginValidationView = new ReactionLoginValidationImpl();
	private final ReactionInformationView reactionInformationView = new ReactionInformationImpl();

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
	@Override
	public ReactionInformationView getReactionInformationView() {
		return reactionInformationView;
	}


}
