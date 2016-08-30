package info.esblurock.reaction.client.panel.contact;

import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class ContactCoordinatesCallback implements AsyncCallback<String> {

	ContactLocationPanel panel;
	
	public ContactCoordinatesCallback(ContactLocationPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.fireToast("City and Country not valid");
	}

	@Override
	public void onSuccess(String result) {
		int pos = result.indexOf(" ");
		String latS = result.substring(0, pos);
		String lng = result.substring(pos);
		panel.fillInCoordinates(latS, lng);
	}

}
