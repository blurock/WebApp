package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.googlecode.gwt.charts.client.Selection;
import com.googlecode.gwt.charts.client.event.SelectEvent;
import com.googlecode.gwt.charts.client.event.SelectHandler;
import com.googlecode.gwt.charts.client.orgchart.OrgChart;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphObjectObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphPredicateNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphStringObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphSubjectNode;
import info.esblurock.reaction.data.rdf.graph.RDFSubTreeParentNode;

public class ChartSelectFieldHandler extends SelectHandler {
	OrgChart chart;
	ArrayList<RDFGraphNode> nodes;
	SearchPanel searchpanel;
	QueryPath toppath;
	public ChartSelectFieldHandler(OrgChart chart, QueryPath path, ArrayList<RDFGraphNode> nodes,SearchPanel toppanel) {
		super();
		this.chart = chart;
		this.nodes = nodes;
		this.searchpanel = toppanel;
		this.toppath = path;
	}
	@Override
	public void onSelect(SelectEvent event) {
		JsArray<Selection> selection = chart.getSelection();
		for(int i=0; i<selection.length(); i++) {
			Selection select = selection.get(i);
			int row = select.getRow().intValue();
			String text = nodes.get(row).toString();
			RDFGraphNode node = nodes.get(row);
			nodeSelected(node, toppath, row);

		}
	} 
	private String nodeSelected(RDFGraphNode node, QueryPath path,int count) {
		String onClickText = node.toString();
		String originalText = node.toString();
		DatabaseObject objectStructure = null;
		if(node.isSubjectNode()) {
			RDFGraphSubjectNode subject = (RDFGraphSubjectNode) node;
			onClickText = subject.getFormattedSubject();
			searchpanel.setUpRDFSubject();
		} else if(node.isPredicateNode()) {
			RDFGraphPredicateNode predicate = (RDFGraphPredicateNode) node;
			onClickText = predicate.getPredicate();
			searchpanel.setUpRDFPredicate();
		} else if(node.isObjectNode()) {
			if(node.getClass().equals(RDFGraphObjectObject.class)) {
				RDFGraphObjectObject object = (RDFGraphObjectObject) node;
				onClickText = object.toString();
				searchpanel.setUpObject(object.getObject());
			} else if(node.getClass().equals(RDFGraphStringObject.class)) {
				RDFGraphStringObject object = (RDFGraphStringObject) node;
				originalText = object.getObject();
				onClickText = object.getFormattedObject();
				searchpanel.setUpRDFObject();
			}
		} else {
			RDFSubTreeParentNode parentnode = (RDFSubTreeParentNode) node;
			onClickText = parentnode.getObject();
			searchpanel.setUpObject(objectStructure);
		}
		if(onClickText == null) {
			onClickText = "---: " + Integer.toString(count);
		}
		String title= Integer.toString(count) + ":" + onClickText;
		searchpanel.setSelected(onClickText,originalText,path);
		return title;
	}

}
