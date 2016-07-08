package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;

public class SetUpProcessesCallback  implements AsyncCallback<Set<String>> {

	String keyword;
	
	public SetUpProcessesCallback(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(Set<String> result) {
		ArrayList<String> lst = new ArrayList<String>();
		Window.alert(result.toString());
		for(String name : result) {
			lst.add(name);
		}
		ValidProcesses valid = new ValidProcesses(keyword);
		valid.setGrid(lst);
		MaterialModal.showModal(valid, TYPE.FIXED_FOOTER);
	}
}
