package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;

import com.googlecode.gwt.charts.client.event.OnMouseOverEvent;
import com.googlecode.gwt.charts.client.event.OnMouseOverHandler;
import com.googlecode.gwt.charts.client.orgchart.OrgChart;

import info.esblurock.reaction.data.rdf.graph.RDFGraphNode;

public class ChartOverFieldHandler extends OnMouseOverHandler {
	OrgChart chart;
	ArrayList<RDFGraphNode> nodes; 
	
	public ChartOverFieldHandler(OrgChart chart, ArrayList<RDFGraphNode> nodes) {
		this.chart = chart;
		this.nodes = nodes;
	}
	
	@Override
	public void onMouseOver(OnMouseOverEvent event) {
		int row = event.getRow().intValue();
	}

}
