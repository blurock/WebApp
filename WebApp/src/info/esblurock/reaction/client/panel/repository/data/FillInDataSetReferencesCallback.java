package info.esblurock.reaction.client.panel.repository.data;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.panel.description.ReferenceDescriptions;
import info.esblurock.reaction.client.panel.description.SetOfReferenceDescriptions;
import info.esblurock.reaction.data.description.DataSetReference;

public class FillInDataSetReferencesCallback implements AsyncCallback<ArrayList<DataSetReference>> {

	SetOfReferenceDescriptions referenceSet;
	
	public FillInDataSetReferencesCallback(SetOfReferenceDescriptions set) {
		this.referenceSet = set;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		
	}

	@Override
	public void onSuccess(ArrayList<DataSetReference> result) {
		for(DataSetReference reference : result) {
			ReferenceDescriptions descr = new ReferenceDescriptions(reference);
			referenceSet.addReference(descr);
		}
		
	}

}
