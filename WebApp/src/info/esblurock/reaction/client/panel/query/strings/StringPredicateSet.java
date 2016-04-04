package info.esblurock.reaction.client.panel.query.strings;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.panel.query.objects.QueryObjectSet;

public class StringPredicateSet extends Composite implements HasText {

	private static StringPredicateSetUiBinder uiBinder = GWT.create(StringPredicateSetUiBinder.class);

	interface StringPredicateSetUiBinder extends UiBinder<Widget, StringPredicateSet> {
	}

	public StringPredicateSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	String restObjectS = "...";
	
	@UiField
	MaterialLink predicate;
	@UiField
	MaterialLink actions;
	@UiField
	MaterialCollapsibleItem item;
	@UiField
	MaterialIcon actionicon;
	
	boolean restObjects;
	ArrayList<String> stringKeys;
	QueryStringSet set;
	MaterialCollapsible collapse;
	String key;
	
	public StringPredicateSet(String p) {
		initWidget(uiBinder.createAndBindUi(this));
		this.restObjects = false;
			predicate.setText(p);
	}
	public StringPredicateSet(String key, ArrayList<String> stringkeys, 
			QueryStringSet set, MaterialCollapsible collapse) {
		initWidget(uiBinder.createAndBindUi(this));
		this.restObjects = true;
		this.key = key;
		this.set = set;
		this.collapse = collapse;
		predicate.setText(restObjectS);
		actionicon.setIcon("mdi-content-add");
		this.stringKeys = stringkeys;
	}

	public void addItem(Widget result) {
		item.addContent(result);
	}
	
	@UiHandler("actions")
	void onExpandClick(ClickEvent e) {
		if(restObjects) {
			this.removeFromParent();
			set.displayObjects(key, stringKeys, collapse);
		}
	}


	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
