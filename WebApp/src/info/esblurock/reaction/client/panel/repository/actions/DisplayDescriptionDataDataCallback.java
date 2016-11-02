package info.esblurock.reaction.client.panel.repository.actions;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.panel.repository.RepositoryBaseItem;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class DisplayDescriptionDataDataCallback implements AsyncCallback<DescriptionDataData> {

	RepositoryBaseItem itemDisplay;
	
	public DisplayDescriptionDataDataCallback(RepositoryBaseItem itemDisplay) {
		this.itemDisplay = itemDisplay;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(DescriptionDataData result) {
		DataDescription description = new DataDescription();
		description.fill(result);
		itemDisplay.addDescriptionDataData(description);
	}

}
