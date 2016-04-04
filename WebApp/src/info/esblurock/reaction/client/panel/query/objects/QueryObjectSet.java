package info.esblurock.reaction.client.panel.query.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.strings.StringPredicateSet;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;

/**
 * The Class QueryObjectSet.
 */
public class QueryObjectSet extends Composite implements HasText {

	/** The ui binder. */
	private static QueryObjectSetUiBinder uiBinder = GWT.create(QueryObjectSetUiBinder.class);

	/**
	 * The Interface QueryObjectSetUiBinder.
	 */
	interface QueryObjectSetUiBinder extends UiBinder<Widget, QueryObjectSet> {
	}

	/**
	 * Instantiates a new query object set.
	 */
	public QueryObjectSet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/** The object key s. */
	String objectKeyS = "Objects";

	/** The objectlabel. */
	@UiField
	MaterialLink objectlabel;
	
	/** The objectpanel. */
	@UiField
	HTMLPanel objectpanel;
	
	/** The stringsetcollapse. */
	@UiField
	MaterialCollapsible stringsetcollapse;
	
	/** The remove. */
	@UiField
	MaterialLink remove;

	/** The objectset. */
	RDFQueryToStringSet objectset;
	
	/** The top path. */
	QueryPath topPath;
	
	/** The parent. */
	MaterialCollapsible parent;
	
	/** The objects displayed. */
	Set<String> objectsDisplayed;

	/**
	 * Instantiates a new query object set.
	 *
	 * @param stringKey the string key
	 * @param objectset the objectset
	 * @param topPath the top path
	 * @param parent the parent
	 */
	public QueryObjectSet(String stringKey, RDFQueryToStringSet objectset, QueryPath topPath,
			MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));

		this.objectset = objectset;
		this.parent = parent;
		this.topPath = topPath;
		objectlabel.setText(stringKey);

		objectsDisplayed = new HashSet<String>();
		Set<String> stringkeys = objectset.keySet();
		for (String key : stringkeys) {
			ArrayList<String> keyset = objectset.get(key);
			if (stringkeys.size() <= 5) {
				displayObjects(key, keyset,parent);
			} else {
				displayObjectsSubset(key, keyset,parent);
			}
		}
	}

	/**
	 * Display objects.
	 *
	 * @param key the key
	 * @param stringkeys the stringkeys
	 * @param collapse the collapse
	 */
	public void displayObjects(String key, ArrayList<String> keyset, MaterialCollapsible collapse) {
		int count = 0;
		Iterator<String> iter = keyset.iterator();
		while (count < 5 && iter.hasNext()) {
			String subkey = iter.next();
			if(!objectsDisplayed.contains(subkey)) {
				count++;
				objectsDisplayed.add(subkey);
				QueryPath next = topPath.addToNewPath(ObjectQueryResult.shortClassname(key), subkey, true);
				ObjectQueryResult subtext = new ObjectQueryResult(next, key, subkey);
				parent.addItem(subtext);
			}
		}		
	}

	/**
	 * Display objects subset.
	 *
	 * @param key the key
	 * @param stringkeys the stringkeys
	 * @param parent the parent
	 */
	private void displayObjectsSubset(String key, ArrayList<String> stringkeys, MaterialCollapsible parent) {
		StringPredicateSet set = new StringPredicateSet(objectKeyS);
		parent.add(set);
		HTMLPanel toppanel = new HTMLPanel("");
		set.addItem(toppanel);
		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType("Popout");
		toppanel.add(collapse);
		
		displayObjects(key,stringkeys,collapse);
	}

	/**
	 * Gets the unique keys.
	 *
	 * @param stringkeys the stringkeys
	 * @return the unique keys
	 */
	HashSet<String> getUniqueKeys(Set<String> stringkeys) {
		HashSet<String> uniqueset = new HashSet<String>();
		if (objectset.size() > 0) {
			for (String key : stringkeys) {
				ArrayList<String> lst = objectset.get(key);
				for (String subkey : lst) {
					uniqueset.add(subkey);
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
		objectlabel.setText(text);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#getText()
	 */
	public String getText() {
		return objectlabel.getText();
	}

}
