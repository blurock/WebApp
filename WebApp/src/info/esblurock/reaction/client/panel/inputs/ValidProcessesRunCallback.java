package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ValidProcessesRunCallback implements AsyncCallback<String> {
	
	public ValidProcessesRunCallback() {
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(String result) {
		Window.alert(result);
	}

}
