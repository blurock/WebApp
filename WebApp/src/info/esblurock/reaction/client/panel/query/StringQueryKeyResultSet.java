package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

public class StringQueryKeyResultSet extends Composite implements HasText {

	private static StringQueryKeyResultSetUiBinder uiBinder = GWT.create(StringQueryKeyResultSetUiBinder.class);

	interface StringQueryKeyResultSetUiBinder extends UiBinder<Widget, StringQueryKeyResultSet> {
	}

	public StringQueryKeyResultSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialCollapsible set;
	@UiField
	MaterialCollapsibleItem item;
	@UiField
	MaterialLink label;
	@UiField
	MaterialLink remove;
	
	
	QueryPath topPath;
	RDFQueryToStringSet stringset;
	String key;
	QueryStringSet parent;

	public StringQueryKeyResultSet(String stringKey, String key, RDFQueryToStringSet stringset, QueryPath toppath,
			QueryStringSet parent) {
		initWidget(uiBinder.createAndBindUi(this));
		topPath = toppath;
		this.stringset = stringset;
		this.key = key;
		this.parent = parent;
		
		label.setText(key);
		HashSet<String> strings = getUniqueKeys();
		for(String ans : strings) {
			QueryPath next = topPath.addToNewPath(key, stringKey);
			//StringQueryResult subtext = new StringQueryResult(next,ans);
			//set.addItem(subtext);
		}
	}
	private HashSet<String> getUniqueKeys() {
		HashSet<String> set = new HashSet<String>();
		for(String ans : stringset.get(key)) {
			set.add(ans);
		}
		return set;
	}

	
	@UiHandler("remove")
	public void onRemoveClick(ClickEvent e) {
		this.removeFromParent();
	}
	
	public void setText(String text) {
		label.setText(text);
	}

	public String getText() {
		return label.getText();
	}

}
