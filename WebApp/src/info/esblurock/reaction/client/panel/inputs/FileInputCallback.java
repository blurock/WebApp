package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class FileInputCallback implements AsyncCallback<String> {

	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.fireToast(caught.toString());
	}

	@Override
	public void onSuccess(String result) {
		MaterialToast.fireToast("Result: " + result);
	}

}
