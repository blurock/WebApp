package info.esblurock.reaction.client.panel.query.strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryStringSet.
 */
public class QueryStringSet extends Composite implements HasText {

	/** The ui binder. */
	private static QueryStringSetUiBinder uiBinder = GWT.create(QueryStringSetUiBinder.class);

	/**
	 * The Interface QueryStringSetUiBinder.
	 */
	interface QueryStringSetUiBinder extends UiBinder<Widget, QueryStringSet> {
	}

	String restObjectsS = "...";
	/**
	 * Instantiates a new query string set.
	 */
	public QueryStringSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/** The stringlabel. */
	@UiField
	MaterialLink stringlabel;
	
	/** The stringpanel. */
	@UiField
	HTMLPanel stringpanel;
	
	/** The stringsetcollapse. */
	@UiField
	MaterialCollapsible stringsetcollapse;
	
	/** The remove. */
	@UiField
	MaterialLink remove;

	/** The stringset. */
	RDFQueryToStringSet stringset;
	
	/** The parent. */
	MaterialCollapsible parent;
	
	/** The objects displayed. */
	Set<String> objectsDisplayed;


	QueryPath topPath;
	/**
	 * Instantiates a new query string set.
	 *
	 * @param stringKey the string key (this is 'String')
	 * @param stringset the stringset HashMap with key as 
	 * @param topPath the top path
	 * @param parent the parent
	 */
	public QueryStringSet(String stringKey, RDFQueryToStringSet stringset, QueryPath topPath,
			MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.stringset = stringset;
		this.parent = parent;
		this.topPath = topPath;

		stringlabel.setText(stringKey);

		// Set of keys (the predicates)
		
		objectsDisplayed = new HashSet<String>();
		Set<String> stringkeys = stringset.keySet();
		Window.alert(stringkeys.toString());
		for (String key : stringkeys) {
			ArrayList<String> keyset = getUniqueKeys(stringset.get(key));
			if (keyset.size() <= 5) {
				displayObjects(key, keyset,parent);
			} else {
				displayObjectsSubset(key,keyset,parent);
			}
		}
	}
	
	public void displayObjects(String key, ArrayList<String> keyset, MaterialCollapsible collapse) {
		int count = 0;
		Iterator<String> iter = keyset.iterator();
		while (count < 5 && iter.hasNext()) {
			String subkey = iter.next();
			if(!objectsDisplayed.contains(subkey)) {
				count++;
				QueryPath next = topPath.addToNewPath(key, subkey,false);
				StringQueryResult subtext = new StringQueryResult(next, key, subkey,stringset.isKeysAsObject());
				collapse.addItem(subtext);
			}
		}
		if(iter.hasNext()) {
			ArrayList<String> restlist = new ArrayList<String>();
			while(iter.hasNext()) {
				restlist.add(iter.next());
			}
			StringPredicateSet set = new StringPredicateSet(key,restlist,this,collapse);
			collapse.addItem(set);
		}
	}
	
	private void displayObjectsSubset(String key, ArrayList<String> stringkeys, MaterialCollapsible parent) {
		
		StringPredicateSet set = new StringPredicateSet(key);
		HTMLPanel toppanel = new HTMLPanel("");
		set.addItem(toppanel);
		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType("Popout");
		toppanel.add(collapse);
		parent.addItem(set);
		displayObjects(key,stringkeys,collapse);
	}
	
	/**
	 * Gets the unique keys.
	 *
	 * @param stringkeys the stringkeys
	 * @return the unique keys
	 */
	ArrayList<String> getUniqueKeys(ArrayList<String> stringkeys) {
		ArrayList<String> uniqueset = new ArrayList<String>();
		if(stringkeys != null) {
		if (stringset.size() > 0) {
			for (String key : stringkeys) {
				if(!uniqueset.contains(key)) {
					uniqueset.add(key);
				}
			}
		}
		}
		return uniqueset;
	}

	/**
	 * On click.
	 *
	 * @param e the e
	 */
	@UiHandler("remove")
	void onClick(ClickEvent e) {
		parent.remove(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
	 */
	public void setText(String text) {
		stringlabel.setText(text);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#getText()
	 */
	public String getText() {
		return stringlabel.getText();
	}

}
