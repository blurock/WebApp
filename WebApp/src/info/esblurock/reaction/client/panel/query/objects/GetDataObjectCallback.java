package info.esblurock.reaction.client.panel.query.objects;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.client.panel.data.BaseDataPresentation;
import info.esblurock.reaction.client.panel.data.DataPresentation;

public class GetDataObjectCallback implements AsyncCallback<DatabaseObject> {

	ObjectQueryResult displayLine;
	
	public GetDataObjectCallback(ObjectQueryResult displayLine) {
		super();
		this.displayLine = displayLine;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		
	}

	@Override
	public void onSuccess(DatabaseObject result) {
		String classname = displayLine.getClassname().getText();		
		DataPresentation presentation = DataPresentation.valueOf(classname);
		String oneline = presentation.asOnLine(result);
		displayLine.setObject(result);
		displayLine.getTextarea().setText(oneline);
		displayLine.activateActions();
	}

}
