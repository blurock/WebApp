package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.event.ReadyEvent;
import com.googlecode.gwt.charts.client.event.ReadyHandler;
import com.googlecode.gwt.charts.client.format.PatternFormat;
import com.googlecode.gwt.charts.client.orgchart.OrgChart;
import com.googlecode.gwt.charts.client.orgchart.OrgChartOptions;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphObjectObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphPredicateNode;
import info.esblurock.reaction.data.rdf.graph.RDFGraphStringObject;
import info.esblurock.reaction.data.rdf.graph.RDFGraphSubjectNode;
import info.esblurock.reaction.data.rdf.graph.RDFSubTreeParentNode;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.SetOfGraphNodes;

public class BasicObjectSearchAsTreeCallback  implements AsyncCallback<RDFTreeNode> {
	QueryPath topPath;
	DataTable dataTable;
	RDFTreeNode anstree;
	SearchPanel searchpanel;
	public BasicObjectSearchAsTreeCallback(QueryPath path, SearchPanel searchpanel) {
		topPath = path;
		this.searchpanel = searchpanel;
	}
	@Override
	public void onFailure(Throwable caught) {
		if (caught.toString().contains("NO LOGIN")) {
			MaterialToast.fireToast("User must be logged in to use query");
		} else {
			Window.alert("Failure: " + caught.toString());
		}
	}

	class ChartRun implements Runnable {
		ArrayList<RDFGraphNode> nodes;
		RDFTreeNode anstree;
		public ChartRun(RDFTreeNode anstree, ArrayList<RDFGraphNode> nodes) {
			this.anstree = anstree;
			this.nodes = nodes;
		}
		@Override
		public void run() {
			dataTable = startBuildDataTable(this.anstree, nodes);
			PatternFormat format = PatternFormat.create("{0}<div style=\"color:red; font-style:italic\">{1}</div>");
			format.format(dataTable, 0, 2);
			
			// Set options
			OrgChartOptions options1 = OrgChartOptions.create();
			options1.setAllowHtml(true);
			//options1.setWidth(400);

			// Draw the chart
			OrgChart chart = new OrgChart();
			chart.draw(dataTable, options1);
			chart.setWidth("500px");
			
			ChartSelectFieldHandler handler = new ChartSelectFieldHandler(chart,topPath,nodes,searchpanel);
			chart.addSelectHandler(handler);
			
			ReadyHandler ready = new ReadyHandler() {
				
				@Override
				public void onReady(ReadyEvent event) {
				}
			};
			chart.addReadyHandler(ready);
			
			ChartOverFieldHandler over = new ChartOverFieldHandler(chart,nodes);
			chart.addOnMouseOverHandler(over);
			ScrollPanel scroll = new ScrollPanel(chart);
			scroll.setWidth("100%");
			searchpanel.addResultPanel(scroll);
		}
		
	}
	@Override
	public void onSuccess(RDFTreeNode anstree) {
		this.anstree = anstree;
		if(anstree.isOverflow()) {
			MaterialToast.fireToast("Query exceeds result limit: try more specific query");
		}
		ArrayList<RDFGraphNode> nodes = new ArrayList<RDFGraphNode>();
		findNodes(anstree,nodes);
		ChartRun chartrun = new ChartRun(anstree,nodes);
		
		ChartLoader chartLoader = new ChartLoader(ChartPackage.ORGCHART);
		chartLoader.loadApi(chartrun);
	}
	public DataTable startBuildDataTable(RDFTreeNode result, ArrayList<RDFGraphNode> nodes) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Name");
		dataTable.addColumn(ColumnType.STRING, "Parent");
		dataTable.addColumn(ColumnType.STRING, "ToolTip");
		
		dataTable.addRows(nodes.size());
		String parent = "";
		int count = 0;
		buildDataTable(result,nodes,null,count,parent,dataTable);
		return dataTable;
	}
	
	private int buildDataTable(RDFTreeNode result, ArrayList<RDFGraphNode> nodes,
			QueryPath path,int count, String parent, DataTable dataTable) {
		RDFGraphNode node = result.getNode();

		parent = setupFromNode(node,null,count,parent,dataTable);
		count++;
		for(RDFTreeNode child : result.getChildren()) {
			count = buildDataTable(child,nodes,path,count,parent,dataTable);
		}
		return count;
	}
	private void findNodes(RDFTreeNode result, ArrayList<RDFGraphNode> nodes) {
		int count = nodes.size();
		nodes.add(result.getParent());
		count++;
		SetOfGraphNodes children = result.getChildren();
		for(RDFTreeNode node : children) {
			findNodes(node,nodes);			
		}
	}
	private int findMatchingNode(RDFGraphNode node, ArrayList<RDFGraphNode> nodes) {
		int ans = 0;
		boolean notfound = true;
		while(ans < nodes.size() && notfound) {
			if(node.compareTo(nodes.get(ans)) == 0) {
				notfound = false;
			}
		}
		return ans;
	}
	private String setupFromNode(RDFGraphNode node, QueryPath path,int count, String parent, DataTable dataTable) {
		String onClickText = node.toString();
		String originalText = node.toString();
		if(node.isSubjectNode()) {
			RDFGraphSubjectNode subject = (RDFGraphSubjectNode) node;
			originalText = subject.getSubject();
			onClickText = subject.getFormattedSubject();
		} else if(node.isPredicateNode()) {
			RDFGraphPredicateNode predicate = (RDFGraphPredicateNode) node;
			onClickText = predicate.getPredicate();
		} else if(node.isObjectNode()) {
			if(node.getClass().equals(RDFGraphObjectObject.class)) {
				RDFGraphObjectObject object = (RDFGraphObjectObject) node;
				onClickText = object.toString();
			} else if(node.getClass().equals(RDFGraphStringObject.class)) {
				RDFGraphStringObject object = (RDFGraphStringObject) node;
				originalText = object.getObject();
				onClickText = object.getFormattedObject();
			}
		} else {
			RDFSubTreeParentNode parentnode = (RDFSubTreeParentNode) node;
			onClickText = parentnode.getObject();
		}
		if(onClickText == null) {
			onClickText = "---: " + Integer.toString(count);
		}
		String title= Integer.toHexString(count) + ":" + onClickText;
		dataTable.setValue(count, 0, title);
		dataTable.setValue(count, 1, parent);
		dataTable.setValue(count, 2, originalText);
		return title;
	}

}
