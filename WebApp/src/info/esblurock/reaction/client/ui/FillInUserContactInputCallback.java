package info.esblurock.reaction.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;

public class FillInUserContactInputCallback implements AsyncCallback<UserDescriptionData> {

	UserContactInput panel;
	
	public FillInUserContactInputCallback(UserContactInput panel) {
		this.panel = panel;
	}
	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.fireToast("No profile found, fill in information");
	}

	@Override
	public void onSuccess(UserDescriptionData result) {
		MaterialToast.fireToast("Profile found for " + result.getDescription().getKeyword());
		panel.fill(result);
	}

}
