package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;
import java.util.Set;

import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialTextArea;
import info.esblurock.reaction.client.panel.query.objects.QueryObjectSet;
import info.esblurock.reaction.client.panel.query.strings.QueryStringSet;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

public class BasicSearchCallback implements AsyncCallback<RDFBySubjectSet> {

	MaterialCollapsibleItem topSearch;
	QueryPath topPath;

	public BasicSearchCallback(QueryPath path, MaterialCollapsibleItem search) {
		topSearch = search;
		topPath = path;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(RDFBySubjectSet answer) {
		Set<String> keys = answer.keySet();

		HTMLPanel toppanel = new HTMLPanel("");

		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType(CollapsibleType.POPOUT);
		topSearch.add(collapse);

		for (String key : keys) {
			SetOfKeywordQueryAnswers result = answer.get(key);
			String stringKey = "String";
			RDFQueryToStringSet stringset = result.get(stringKey);
			if (stringset.size() > 0) {
				QueryStringSet stringitem = new QueryStringSet(stringKey, stringset, topPath,collapse);
			}

			String objectKey = "Object";
			RDFQueryToStringSet objectset = result.get(objectKey);
			if (objectset.size() > 0) {
				QueryObjectSet objectitem = new QueryObjectSet(objectKey, objectset, topPath,collapse);
			}
		}
	}

}
