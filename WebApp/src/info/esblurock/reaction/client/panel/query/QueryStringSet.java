package info.esblurock.reaction.client.panel.query;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
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
	
	public QueryStringSet(String stringKey, RDFQueryToStringSet stringset, QueryPath topPath, MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.stringset = stringset;
		this.parent = parent;
		
		stringlabel.setText(stringKey);

		Set<String> stringkeys = stringset.keySet();

		for (String key : stringkeys) {
			if (stringset.size() > 0) {
				StringQueryKeyResultSet item = new StringQueryKeyResultSet(stringKey, key, stringset, topPath,this);
				stringsetcollapse.addItem(item);
			}
		}

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
