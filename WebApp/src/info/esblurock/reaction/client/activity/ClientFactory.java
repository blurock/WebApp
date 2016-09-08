package info.esblurock.reaction.client.activity;
//import info.blurock.hellomvp.client.ui.HelloView;

import info.esblurock.reaction.client.ui.ReactionFirstView;
import info.esblurock.reaction.client.ui.ReactionInformationView;
import info.esblurock.reaction.client.ui.ReactionLoginValidationView;
import info.esblurock.reaction.client.ui.ReactionQueryView;
import info.esblurock.reaction.client.ui.ReactionTopView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	ReactionTopView getReactionTopView();
	ReactionFirstView getReactionFirstView();
	ReactionQueryView getReactionQueryView();
	ReactionLoginValidationView getReactionLoginValidationView();
	ReactionInformationView getReactionInformationView();
}
