package info.esblurock.reaction.client.panel.repository.actions;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.repository.RepositoryPath;
import info.esblurock.reaction.client.panel.repository.RepositoryTopNode;
import info.esblurock.reaction.data.repository.DataPathName;
import info.esblurock.reaction.data.repository.ListOfRepositoryDataSources;

public class RetrieveDataSetPathCallback implements AsyncCallback<ListOfRepositoryDataSources> {

	RepositoryTopNode topNode;
	
	public RetrieveDataSetPathCallback(RepositoryTopNode topNode) {
		this.topNode = topNode;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		
	}

	@Override
	public void onSuccess(ListOfRepositoryDataSources result) {
		for(DataPathName dataset : result) {
			RepositoryPath path = dataset.asRepositoryPath();
			topNode.addPath(path);
		}
		
	}

}
