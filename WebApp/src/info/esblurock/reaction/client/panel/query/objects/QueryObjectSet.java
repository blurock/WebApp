package info.esblurock.reaction.client.panel.query.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

public class QueryObjectSet extends Composite implements HasText {

	private static QueryObjectSetUiBinder uiBinder = GWT.create(QueryObjectSetUiBinder.class);

	interface QueryObjectSetUiBinder extends UiBinder<Widget, QueryObjectSet> {
	}

	public QueryObjectSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink objectlabel;
	@UiField
	HTMLPanel objectpanel;
	@UiField
	MaterialCollapsible stringsetcollapse;
	@UiField
	MaterialLink remove;
	
	RDFQueryToStringSet objectset;
	MaterialCollapsible parent;
	
	public QueryObjectSet(String stringKey, RDFQueryToStringSet objectset, QueryPath topPath, MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.objectset = objectset;
		this.parent = parent;
		
		objectlabel.setText(stringKey);
		Set<String> stringkeys = objectset.keySet();

		for (String key : stringkeys) {
			ArrayList<String>  keyset = objectset.get(key);
			for (String subkey : keyset) {
				QueryPath next = topPath.addToNewPath(ObjectQueryResult.shortClassname(key), subkey,true);
				ObjectQueryResult subtext = new ObjectQueryResult(next, key, subkey);
				parent.addItem(subtext);
			}
		}
	}

	HashSet<String> getUniqueKeys(Set<String> stringkeys) {
		HashSet<String> uniqueset = new HashSet<String>();
		if (objectset.size() > 0) {
			for (String key : stringkeys) {
				ArrayList<String> lst = objectset.get(key);
				for (String subkey : lst) {
					uniqueset.add(subkey);
				}
			}
		}
		return uniqueset;
	}

	@UiHandler("remove")
	void onClick(ClickEvent e) {
		parent.remove(this);
	}
	
	public void setText(String text) {
		objectlabel.setText(text);
	}

	public String getText() {
		return objectlabel.getText();
	}

}
