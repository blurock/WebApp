package info.esblurock.reaction.client.panel.repository;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.panel.repository.actions.RetrieveDataSetPathCallback;

public class RepositoryTopNode  extends Composite implements HasText {

	private static RepositoryTopNodeUiBinder uiBinder = GWT.create(RepositoryTopNodeUiBinder.class);

	interface RepositoryTopNodeUiBinder extends UiBinder<Widget, RepositoryTopNode> {
	}
	
	public RepositoryTopNode() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink stringlabel;

	@UiField
	MaterialCollapsible repository;
	@UiField
	MaterialLink refresh;
	
	RepositoryBaseItem topnode;
	String toplabel;
	
	public RepositoryTopNode(String toplabel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.toplabel = toplabel;
		initDisplay();
	}
	
	public void initDisplay() {
		repository.clear();
		RepositoryPath newpath = new RepositoryPath();
		topnode = new RepositoryBaseItem(toplabel,newpath,repository);
		repository.add(topnode);
	}
	
	public RepositoryBaseItem addPath(RepositoryPath path) {
		RepositoryBaseItem subitem = topnode;
		for(String label : path) {
			RepositoryBaseItem pathitem = subitem.getSubItem(label);
			if(pathitem == null) {
				subitem = subitem.addSubItemLabel(label);
			} else {
				subitem = pathitem;
			}
		}
		subitem.leaveSettings();
		return subitem;
	}
	@UiHandler("refresh")
	void onRefresh(ClickEvent event) {
		initDisplay();
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		RetrieveDataSetPathCallback callback = new RetrieveDataSetPathCallback(this);
		async.getRepositoryDataSources(callback);
	}
	
	
	
	public void setText(String text) {
		stringlabel.setText(text);
	}

	public String getText() {
		return stringlabel.getText();
	}

}
