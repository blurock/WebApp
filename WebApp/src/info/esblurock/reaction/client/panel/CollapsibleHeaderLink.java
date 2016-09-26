package info.esblurock.reaction.client.panel;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.data.BaseDataPresentation;
import info.esblurock.reaction.client.panel.data.DataPresentation;
import info.esblurock.reaction.client.panel.query.AddQueryResult;
import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.QueryPathElement;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphObjectObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphPredicateNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphStringObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphSubjectNode;
import info.esblurock.reaction.data.rdf.graph.RDFSubTreeParentNode;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.SetOfGraphNodes;

public class CollapsibleHeaderLink extends Composite implements HasText {

	private static CollapsibleHeaderLinkUiBinder uiBinder = GWT.create(CollapsibleHeaderLinkUiBinder.class);

	interface CollapsibleHeaderLinkUiBinder extends UiBinder<Widget, CollapsibleHeaderLink> {
	}

	@UiField
	MaterialLink link;
	@UiField
	MaterialLink rest;
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
	DatabaseObject objectStructure;
	List<RDFTreeNode> restlst;
	AddQueryResult addnode;
	MaterialCollapsible parent;
	
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
		
		objectStructure = null;
	}
	public CollapsibleHeaderLink(RDFGraphNode node,QueryPath path) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		setupMaterialLinkFromNode(node,path);
	}

	public CollapsibleHeaderLink(MaterialCollapsible parent, List<RDFTreeNode> restlst, QueryPath path) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.restlst = restlst;
		this.parent = parent;
		linkpath = path;
		rest.setText("Click for more");
		objectStructure = null;
		
		rest.setVisible(true);
		rest.setVisible(true);
		link.setVisible(false);
		link.setEnabled(false);
		expandnext.setVisible(false);
		expandnext.setEnabled(false);
		objectinfo.setVisible(false);
		objectinfo.setEnabled(false);
		
	}
	
	private void setupMaterialLinkFromNode(RDFGraphNode node, QueryPath path) {
		String onClickText = node.toString();
		objectStructure = null;
		rest.setVisible(false);
		rest.setVisible(false);
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
				objectStructure = object.getObject();
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

	@UiHandler("rest")
	public void onRestClick(ClickEvent event) {
		addnode = new AddQueryResult();
		addnode.addChildren(parent, restlst, linkpath);
		this.removeFromParent();
	}
	
	@UiHandler("link")
	public void onLinkClick(ClickEvent event) {
		if(objectStructure != null) {
			String classname = objectStructure.getClass().getName();
			MaterialToast.fireToast(classname);
			int pos = classname.lastIndexOf('.');
			String shortname = classname.substring(pos+1);
			DataPresentation presentation = DataPresentation.valueOf(shortname);
			BaseDataPresentation display = presentation.asDisplayObject(objectStructure);
			body.add(display);
			display.openModal();
		}
	}
	@UiHandler("expandnext")
	public void onExpandClick(ClickEvent event) {
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(linkpath, collapsible);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.searchedRegisteredQueries(link.getText(), callback);		
	}
	@UiHandler("deletesub")
	public void onDelete(ClickEvent event) {
		this.removeFromParent();
	}
	@UiHandler("objectinfo")
	public void onInformation(ClickEvent event) {
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
