package info.esblurock.reaction.client.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import gwt.material.design.client.ui.MaterialCollapsible;
import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;

public class QueryClickHandler implements ClickHandler {
	
	MaterialCollapsible item;
	String key;
	QueryPath path;
	
	public QueryClickHandler(String key, QueryPath path, MaterialCollapsible item) {
		this.item = item;
		this.key = key;
		this.path = path;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, item);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.searchedRegisteredQueries(key, callback); 
	}

}
