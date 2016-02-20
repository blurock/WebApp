package info.esblurock.reaction.client.panel.query;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

public class BasicObjectSearchCallback implements AsyncCallback<RDFBySubjectSet> {

	MaterialCollapsibleItem topSearch;
	QueryPath topPath;

	public BasicObjectSearchCallback(QueryPath path, MaterialCollapsibleItem search) {
		topSearch = search;
		topPath = path;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(RDFBySubjectSet answers) {
		HTMLPanel toppanel = new HTMLPanel("");
		topSearch.addContent(toppanel);
		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType("Popout");
		toppanel.add(collapse);

		for (String key : answers.keySet()) {
			SetOfKeywordQueryAnswers result = answers.get(key);
			if (result != null) {

				String stringKey = "String";
				RDFQueryToStringSet stringset = result.get(stringKey);
				String objectKey = "Object";
				RDFQueryToStringSet objectset = result.get(objectKey);

				if (stringset.size() > 0) {
					QueryStringSet stringitem = new QueryStringSet(stringKey, stringset, topPath, collapse);
					collapse.addItem(stringitem);
				}

				if (objectset.size() > 0) {
					QueryObjectSet objectitem = new QueryObjectSet(objectKey, objectset, topPath, collapse);
					collapse.addItem(objectitem);
				}
			}
		}

	}

}
