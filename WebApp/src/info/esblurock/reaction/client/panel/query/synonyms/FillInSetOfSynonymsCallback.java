package info.esblurock.reaction.client.panel.query.synonyms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;

public class FillInSetOfSynonymsCallback  implements AsyncCallback<HashMap<String, ArrayList<String>>> {

	SetOfSynonymsPanel panel;
	public FillInSetOfSynonymsCallback(SetOfSynonymsPanel panel) {
		this.panel = panel;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(HashMap<String, ArrayList<String>> result) {
		Set<String> keys = result.keySet();
		for(String standard : keys) {
			panel.addStandardKeyword(standard);
			SynonymPanel synpanel = panel.addSynonymPanel(standard);
			ArrayList<String> synonyms = result.get(standard);
			for(String synonym : synonyms) {
				panel.addSynonymToStandard(standard, synonym);
				synpanel.addSynonymToPanel(synonym);
			}
		}
	}

}
