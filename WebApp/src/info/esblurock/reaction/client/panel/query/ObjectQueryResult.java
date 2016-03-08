package info.esblurock.reaction.client.panel.query;

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

import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

public class ObjectQueryResult extends Composite implements HasText {

	private static ObjectQueryResultUiBinder uiBinder = GWT.create(ObjectQueryResultUiBinder.class);

	interface ObjectQueryResultUiBinder extends UiBinder<Widget, ObjectQueryResult> {
	}

	public ObjectQueryResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink textarea;
	@UiField
	MaterialLink classname;
	@UiField
	MaterialLink actions;
	@UiField
	MaterialCollapsibleItem item;

	QueryPath path;

	public ObjectQueryResult(QueryPath path, String classnameS, String result) {
		initWidget(uiBinder.createAndBindUi(this));
		this.path = path;
		
		classname.setText(shortClassname(classnameS));
		textarea.setText(result);
		classname.setTooltip(path.toString());
	}

	@UiHandler("actions")
	public void actionClick(ClickEvent e) {
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		String title = "Object Search: " + textarea.getText();
		MaterialToast.alert(title);
		QueryPath path = new QueryPath(textarea.getText());
		async.objectSearch(textarea.getText(), new BasicObjectSearchCallback(path,item ));
		
	}
	
	private String shortClassname(String classname) {
		int index = 0;
		String shortname = classname;
		while(index >=0) {
			shortname = shortname.substring(index+1);
			index = shortname.indexOf('.');
		}
		return shortname;
	}
	public void setText(String text) {
		textarea.setText(text);
	}

	public String getText() {
		return textarea.getText();
	}

}
