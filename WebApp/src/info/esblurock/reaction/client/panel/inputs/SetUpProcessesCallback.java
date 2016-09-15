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
	MaterialModalContent modalcontent;
	MaterialModal modal;
	
	public SetUpProcessesCallback(String keyword,
			MaterialModal modal,MaterialModalContent modalcontent) {
		this.keyword = keyword;
		this.modalcontent = modalcontent;
		this.modal = modal;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(List<String> result) {
		ArrayList<String> lst = new ArrayList<String>();
		Window.alert(result.toString());
		for(String name : result) {
			lst.add(name);
		}
		ValidProcesses valid = new ValidProcesses(keyword,modal);
		modalcontent.clear();
		modalcontent.add(valid);
		valid.setGrid(lst);
		modal.openModal();
	}
}
