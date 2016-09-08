package info.esblurock.reaction.client.panel;

import com.google.appengine.labs.repackaged.com.google.common.annotations.VisibleForTesting.Visibility;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.inputs.SetUpProcessesCallback;
import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.QueryPathElement;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphObjectObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphPredicateNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphStringObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphSubjectNode;
import info.esblurock.reaction.data.rdf.graph.RDFSubTreeParentNode;

public class CollapsibleHeaderLink extends Composite implements HasText {

	private static CollapsibleHeaderLinkUiBinder uiBinder = GWT.create(CollapsibleHeaderLinkUiBinder.class);

	interface CollapsibleHeaderLinkUiBinder extends UiBinder<Widget, CollapsibleHeaderLink> {
	}

	@UiField
	MaterialLink link;
	@UiField
	MaterialIcon expandnext;
	@UiField
	MaterialIcon deletesub;
	@UiField
	MaterialCollapsibleBody body;
	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialIcon objectinfo;
	
	boolean subjectB;
	boolean predicateB;
	boolean objectstringB;
	boolean objectobjectB;
	boolean searchtextB;
	
	QueryPath linkpath;

	protected void init() {
		subjectB = false;
		predicateB = false;
		objectstringB = false;
		objectobjectB = false;
		searchtextB = false;
		
		expandnext.setVisible(true);
		expandnext.setEnabled(true);
		objectinfo.setVisible(false);
		objectinfo.setEnabled(false);
	}
	public CollapsibleHeaderLink(RDFGraphNode node,QueryPath path) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		setupMaterialLinkFromNode(node,path);
	}

	private void setupMaterialLinkFromNode(RDFGraphNode node, QueryPath path) {
		String onClickText = node.toString();
		if(node.isSubjectNode()) {
			RDFGraphSubjectNode subject = (RDFGraphSubjectNode) node;
			onClickText = subject.getSubject();
			link.setIconType(IconType.LABEL_OUTLINE);
			linkpath = new QueryPath(path,QueryPathElement.SUBJECT,onClickText);
			expandnext.setVisible(true);
			expandnext.setEnabled(true);
			objectinfo.setVisible(false);
			objectinfo.setEnabled(false);
		} else if(node.isPredicateNode()) {
			RDFGraphPredicateNode predicate = (RDFGraphPredicateNode) node;
			onClickText = predicate.getPredicate();
			link.setIconType(IconType.NAVIGATE_NEXT);
			linkpath = new QueryPath(path,QueryPathElement.PREDICATE,onClickText);
			expandnext.setVisible(false);
			expandnext.setEnabled(false);
			objectinfo.setVisible(false);
			objectinfo.setEnabled(false);
		} else if(node.isObjectNode()) {
			if(node.getClass().equals(RDFGraphObjectObject.class)) {
				RDFGraphObjectObject object = (RDFGraphObjectObject) node;
				onClickText = object.toString();
				link.setIconType(IconType.INFO);
				linkpath = new QueryPath(path,QueryPathElement.OBJECTOBJECT,onClickText);
				expandnext.setVisible(false);
				expandnext.setEnabled(false);
				objectinfo.setVisible(true);
				objectinfo.setEnabled(true);
			} else if(node.getClass().equals(RDFGraphStringObject.class)) {
				RDFGraphStringObject object = (RDFGraphStringObject) node;
				onClickText = object.getObject();
				link.setIconType(IconType.CHECK);
				linkpath = new QueryPath(path,QueryPathElement.OBJECTSTRING,onClickText);
				expandnext.setVisible(true);
				expandnext.setEnabled(true);
				objectinfo.setVisible(false);
				objectinfo.setEnabled(false);
			}
		} else {
			RDFSubTreeParentNode parentnode = (RDFSubTreeParentNode) node;
			onClickText = "Search Expand: " + parentnode.getObject();
			link.setIconType(IconType.INFO_OUTLINE);
			linkpath = new QueryPath(path,QueryPathElement.SEARCHSTRING,onClickText);
			link.setTooltip(linkpath.toString());
			expandnext.setVisible(false);
			expandnext.setEnabled(false);
			objectinfo.setVisible(false);
			objectinfo.setEnabled(false);
		}
		if(onClickText == null) {
			link.setText("----");
		} else {
			link.setText(onClickText);
		}
	}

	@UiHandler("link")
	public void onLinkClick(ClickEvent event) {
	}
	@UiHandler("expandnext")
	public void onExpandClick(ClickEvent event) {
		MaterialToast.fireToast("Expand: " + link.getText());
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(linkpath, collapsible);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.searchedRegisteredQueries(link.getText(), callback);		
	}
	@UiHandler("deletesub")
	public void onDelete(ClickEvent event) {
		MaterialToast.fireToast("Delete: " + link.getText());
		this.removeFromParent();
	}
	@Override
	public String getText() {
		return link.getText();
	}

	@Override
	public void setText(String text) {
		link.setText(text);
	}

	public void setIconType(IconType icon) {
		link.setIconType(icon);
	}
	public QueryPath getQueryPath() {
		return linkpath;
	}
	public MaterialCollapsible getCollapsible() {
		return collapsible;
	}
}
