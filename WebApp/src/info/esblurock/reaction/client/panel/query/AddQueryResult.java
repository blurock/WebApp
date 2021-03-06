package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.CollapsibleHeaderLink;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.SetOfGraphNodes;

public class AddQueryResult {

	QueryPath path;

	public AddQueryResult() {
	}

	public void addTreeNode(MaterialCollapsible item, RDFTreeNode toptreenode, QueryPath path, boolean bypassheader) {
		if (bypassheader) {
			addChildren(item, toptreenode, path);
		} else {
			RDFGraphNode parent = toptreenode.getParent();
			CollapsibleHeaderLink link = new CollapsibleHeaderLink(parent, path);
			item.add(link);
			MaterialCollapsible collapse = link.getCollapsible();
			QueryPath subpath = link.getQueryPath();
			addChildren(collapse, toptreenode, subpath);
		}
	}

	public void addRestNode(MaterialCollapsible item, List<RDFTreeNode> restlst,QueryPath path) {
		CollapsibleHeaderLink link = new CollapsibleHeaderLink(item, restlst, path);
		item.add(link);
	}

	public void addChildren(MaterialCollapsible collapse, RDFTreeNode toptreenode, QueryPath path) {
		SetOfGraphNodes children = toptreenode.getChildren();
		List<RDFTreeNode> lst = new ArrayList<RDFTreeNode>(children);
		addChildren(collapse, lst, path);
	}

	public void addChildren(MaterialCollapsible collapse, List<RDFTreeNode> lst, QueryPath path) {
		if (lst != null) {
			Collections.sort(lst);
			Iterator<RDFTreeNode> iter = lst.iterator();
			int count = 0;
			while (iter.hasNext() && count < 5) {
				count++;
				RDFTreeNode treenode = iter.next();
				addTreeNode(collapse, treenode, path, false);
			}
			if (iter.hasNext()) {
				List<RDFTreeNode> restlst = new ArrayList<RDFTreeNode>();
				while (iter.hasNext()) {
					restlst.add(iter.next());
				}
				addRestNode(collapse, restlst,path);
			}
		} else {
			MaterialToast.fireToast("No children");
		}

	}

}
