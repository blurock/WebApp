package info.esblurock.reaction.client.panel.query;

import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

public class ObjectQueryKeyResultSet extends Composite implements HasText {

	private static ObjectQueryKeyResultSetUiBinder uiBinder = GWT.create(ObjectQueryKeyResultSetUiBinder.class);

	interface ObjectQueryKeyResultSetUiBinder extends UiBinder<Widget, ObjectQueryKeyResultSet> {
	}

	public ObjectQueryKeyResultSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialCollapsibleItem item;
	@UiField
	MaterialLink label;
	@UiField
	MaterialLink remove;
	@UiField
	MaterialLink expand;

	QueryPath topPath;
	RDFQueryToStringSet stringset;
	String key;
	QueryObjectSet parent;

	public ObjectQueryKeyResultSet(String stringKey, String key, 
			RDFQueryToStringSet stringset, QueryPath toppath,
			QueryObjectSet parent) {
		initWidget(uiBinder.createAndBindUi(this));
		topPath = toppath;
		this.stringset = stringset;
		this.key = key;
		this.parent = parent;

		
		String classname = getClassNameFromKey(key);
		label.setText(classname);
		label.setTooltip(key);
		if (!stringset.isKeysAsObject()) {
			
			HTMLPanel toppanel = new HTMLPanel("");
			item.addContent(toppanel);
			MaterialCollapsible collapse = new MaterialCollapsible();
			collapse.setType("Popout");
			toppanel.add(collapse);
			
			HashSet<String> strings = getUniqueKeys();
			for (String ans : strings) {
				QueryPath next = topPath.addToNewPath(key, stringKey);
				StringQueryResult subtext = new StringQueryResult(next, ans);
				collapse.addItem(subtext);
			}
		}
	}

	private String getClassNameFromKey(String key) {
		String classname = key;
		while (classname.indexOf('.') > 0) {
			classname = classname.substring(classname.indexOf(".") + 1);
		}
		return classname;
	}

	private HashSet<String> getUniqueKeys() {
		HashSet<String> set = new HashSet<String>();
		for (String ans : stringset.get(key)) {
			set.add(ans);
		}
		return set;
	}

	@UiHandler("remove")
	void onClick(ClickEvent e) {
		parent.removeItem(this);
	}

	@UiHandler("expand")
	void onExpandClick(ClickEvent e) {
		HashSet<String> keys = getUniqueKeys();
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(topPath, item);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.mergeSearch(keys, callback);
	}

	public void setText(String text) {
		label.setText(text);
	}

	public String getText() {
		return label.getText();
	}

}
