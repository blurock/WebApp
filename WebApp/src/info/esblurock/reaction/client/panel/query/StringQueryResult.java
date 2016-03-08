package info.esblurock.reaction.client.panel.query;

import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;

public class StringQueryResult extends Composite implements HasText {

	private static StringQueryResultUiBinder uiBinder = GWT.create(StringQueryResultUiBinder.class);

	interface StringQueryResultUiBinder extends UiBinder<Widget, StringQueryResult> {
	}

	
	public StringQueryResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink predicate;
	@UiField
	MaterialLink textarea;
	@UiField
	MaterialLink actions;
	@UiField
	MaterialCollapsibleItem item;
	
	QueryPath path;
	
	public StringQueryResult(QueryPath path, String p, String result) {
		initWidget(uiBinder.createAndBindUi(this));
			this.path = path;
			predicate.setText(p);
			
			if(result.length() < 60) {
				textarea.setText(result);
			} else {
				String substring = result.substring(0,56) + "...";
				textarea.setText(substring);
			}
			predicate.setTooltip(path.toString());			
	}

	
	@UiHandler("actions")
	void onExpandClick(ClickEvent e) {
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, item);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.singleKeyQuery(textarea.getText(), callback);
	}

	public void setText(String text) {
		textarea.setText(text);
	}

	public String getText() {
		return textarea.getText();
	}

}
