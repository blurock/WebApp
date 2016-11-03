package info.esblurock.reaction.client.panel.repository.actions;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.repository.RepositoryBaseItem;
import info.esblurock.reaction.client.panel.repository.RepositoryPath;
import info.esblurock.reaction.client.panel.repository.RepositoryTopNode;
import info.esblurock.reaction.data.repository.DataPathName;
import info.esblurock.reaction.data.repository.ListOfRepositoryDataSources;

public class RetrieveDataSetPathCallback implements AsyncCallback<ListOfRepositoryDataSources> {

	MaterialCollapsible topsearch;
	RepositoryBaseItem topnode;
	public RetrieveDataSetPathCallback(MaterialCollapsible topsearch) {
		this.topsearch = topsearch;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		
	}

	@Override
	public void onSuccess(ListOfRepositoryDataSources result) {
		topnode = new RepositoryBaseItem("Repostiory");
		topsearch.add(topnode);
		for(DataPathName dataset : result) {
			RepositoryPath path = dataset.asRepositoryPath();
			addPath(path);
		}
		
	}
	public RepositoryBaseItem addPath(RepositoryPath path) {
		RepositoryBaseItem subitem = topnode;
		for(String label : path) {
			RepositoryBaseItem pathitem = subitem.getSubItem(label);
			if(pathitem == null) {
				subitem = subitem.addSubItemLabel(label);
			} else {
				subitem = pathitem;
			}
		}
		subitem.leaveSettings();
		return subitem;
	}

}
