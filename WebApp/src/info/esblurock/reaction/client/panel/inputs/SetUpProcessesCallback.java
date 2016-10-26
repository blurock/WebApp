package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;

public class SetUpProcessesCallback  implements AsyncCallback<List<String>> {

	String keyword;
	SetOfInputs inputs;
	
	public SetUpProcessesCallback(String keyword, SetOfInputs inputs) {
		this.keyword = keyword;
		this.inputs = inputs;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(List<String> result) {
		Window.alert("SetUpProcessesCallback: onSuccess: '" + keyword + "': " + result);
		inputs.showValidProcesses(keyword, result);
	}
}
