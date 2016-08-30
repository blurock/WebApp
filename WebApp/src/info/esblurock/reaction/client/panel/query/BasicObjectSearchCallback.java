package info.esblurock.reaction.client.panel.query;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.query.objects.QueryObjectSet;
import info.esblurock.reaction.client.panel.query.strings.QueryStringSet;
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
		if(caught.toString().contains("NO LOGIN")) {
			MaterialToast.fireToast("User must be logged in to use query");
		} else {
		Window.alert(caught.toString());
		}
	}

	@Override
	public void onSuccess(RDFBySubjectSet answers) {
		if(answers.size() > 0) {
		HTMLPanel toppanel = new HTMLPanel("");
		topSearch.add(toppanel);
		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType(CollapsibleType.POPOUT);
		toppanel.add(collapse);
		for (String key : answers.keySet()) {
			SetOfKeywordQueryAnswers result = answers.get(key);
			if (result != null) {
				String stringKey = "String";
				RDFQueryToStringSet stringset = result.get(stringKey);
				String objectKey = "Object";
				RDFQueryToStringSet objectset = result.get(objectKey);

				//Window.alert("String: " + Integer.toString(stringset.size()));
				if (stringset.size() > 0) {
					QueryStringSet stringitem = new QueryStringSet(stringKey, stringset, topPath, collapse);
					//collapse.addItem(stringitem);
				}

				//Window.alert("Object: " + Integer.toString(objectset.size()));
				if (objectset.size() > 0) {
					QueryObjectSet objectitem = new QueryObjectSet(objectKey, objectset, topPath, collapse);
					//collapse.addItem(objectitem);
				}
			}
		}
		} else {
			MaterialToast.fireToast("No objects found in Query");
		}
	}

}
