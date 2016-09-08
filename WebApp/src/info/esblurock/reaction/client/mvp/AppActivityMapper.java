package info.esblurock.reaction.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.activity.ClientFactory;
import info.esblurock.reaction.client.activity.ReactionFirstActivity;
import info.esblurock.reaction.client.activity.ReactionInformationActivity;
import info.esblurock.reaction.client.activity.ReactionLoginValidationActivity;
import info.esblurock.reaction.client.activity.ReactionQueryActivity;
import info.esblurock.reaction.client.activity.ReactionTopActivity;
import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.activity.place.ReactionInformationPlace;
import info.esblurock.reaction.client.activity.place.ReactionLoginValidationPlace;
import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.ui.login.AsyncGetUserData;
import info.esblurock.reaction.client.ui.login.UserDTO;


public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ReactionTopPlace) {
			return new ReactionTopActivity((ReactionTopPlace) place, clientFactory);
		} else if (place instanceof ReactionQueryPlace) {
			return new ReactionQueryActivity((ReactionQueryPlace) place, clientFactory);
		} else if (place instanceof ReactionFirstPlace) {
			return new ReactionFirstActivity((ReactionFirstPlace) place, clientFactory);
		} else if (place instanceof ReactionLoginValidationPlace) {
			return new ReactionLoginValidationActivity((ReactionLoginValidationPlace) place, clientFactory);
		} else if( place instanceof ReactionInformationPlace) {
			return new ReactionInformationActivity((ReactionInformationPlace) place, clientFactory);
		}
	
		return null;
	}
}