package info.esblurock.reaction.client.panel.query.strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

public class QueryStringSet extends Composite implements HasText {

	private static QueryStringSetUiBinder uiBinder = GWT.create(QueryStringSetUiBinder.class);

	interface QueryStringSetUiBinder extends UiBinder<Widget, QueryStringSet> {
	}

	public QueryStringSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink stringlabel;
	@UiField
	HTMLPanel stringpanel;
	@UiField
	MaterialCollapsible stringsetcollapse;
	@UiField
	MaterialLink remove;

	RDFQueryToStringSet stringset;
	MaterialCollapsible parent;

	public QueryStringSet(String stringKey, RDFQueryToStringSet stringset, QueryPath topPath,
			MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.stringset = stringset;
		this.parent = parent;
		stringlabel.setText(stringKey);

		Set<String> stringkeys = stringset.keySet();
		for (String key : stringkeys) {
			HashSet<String> uniqueset = new HashSet<String>();
			ArrayList<String> lst = stringset.get(key);
			for (String subkey : lst) {
				uniqueset.add(subkey);
			}
			for (String subkey : uniqueset) {
				QueryPath next = topPath.addToNewPath(key, subkey,false);
				StringQueryResult subtext = new StringQueryResult(next, key, subkey,stringset.isKeysAsObject());
				parent.addItem(subtext);
			}
		}
	}
	HashSet<String> getUniqueKeys(Set<String> stringkeys) {
		HashSet<String> uniqueset = new HashSet<String>();
		if (stringset.size() > 0) {
			for (String key : stringkeys) {
				ArrayList<String> lst = stringset.get(key);
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
		stringlabel.setText(text);
	}

	public String getText() {
		return stringlabel.getText();
	}

}
