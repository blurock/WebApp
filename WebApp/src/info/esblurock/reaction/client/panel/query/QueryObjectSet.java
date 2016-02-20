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
			if(objectset.size() > 0) {
				ObjectQueryKeyResultSet item = new ObjectQueryKeyResultSet(stringKey, key, objectset, topPath, this);
				stringsetcollapse.addItem(item);
			}
		}
	}

	public void removeItem(ObjectQueryKeyResultSet item) {
		stringsetcollapse.remove(item);
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
