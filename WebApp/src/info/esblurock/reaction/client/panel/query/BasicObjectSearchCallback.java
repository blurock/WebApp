package info.esblurock.reaction.client.panel.query;

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
			Window.alert(caught.toString());
		}
	}

	@Override
	public void onSuccess(RDFTreeNode toptreenode) {
		addTreeNode(topSearch, toptreenode, topPath,true);
	}

	private void addTreeNode(MaterialCollapsible item, 
			RDFTreeNode toptreenode, QueryPath path, boolean bypassheader) {
		if(bypassheader) {
			addChildren(item,toptreenode,path);
		} else {
			RDFGraphNode parent = toptreenode.getParent();
			CollapsibleHeaderLink link = new CollapsibleHeaderLink(parent, path);
			item.add(link);
			MaterialCollapsible collapse = link.getCollapsible();
			QueryPath subpath = link.getQueryPath();
			addChildren(collapse,toptreenode,subpath);
		}
	}
	private void addChildren(MaterialCollapsible collapse, RDFTreeNode toptreenode, QueryPath path) {
		SetOfGraphNodes children = toptreenode.getChildren();
		if (children != null) {
			for (RDFTreeNode treenode : children) {
				addTreeNode(collapse,treenode, path,false);
			}
		} else {
			MaterialToast.fireToast("No children");
		}
		
	}
}
