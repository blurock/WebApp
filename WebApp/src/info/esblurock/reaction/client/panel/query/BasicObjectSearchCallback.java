package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.CollapsibleHeaderLink;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.SetOfGraphNodes;

public class BasicObjectSearchCallback implements AsyncCallback<RDFTreeNode> {

	MaterialCollapsible topSearch;
	QueryPath topPath;

	public BasicObjectSearchCallback(QueryPath path, MaterialCollapsible search) {
		topSearch = search;
		topPath = path;
	}

	@Override
	public void onFailure(Throwable caught) {
		if (caught.toString().contains("NO LOGIN")) {
			MaterialToast.fireToast("User must be logged in to use query");
		} else {
			Window.alert("Failure: " + caught.toString());
		}
	}

	@Override
	public void onSuccess(RDFTreeNode toptreenode) {
		if(toptreenode.isOverflow()) {
			MaterialToast.fireToast("Query exceeds result limit: try more specific query");
		}
		AddQueryResult addnode = new AddQueryResult();
		addnode.addTreeNode(topSearch, toptreenode, topPath,true);
	}

}
