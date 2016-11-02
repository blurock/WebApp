package info.esblurock.reaction.client.panel.repository;

import java.util.HashMap;
import java.util.HashSet;
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

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.panel.repository.actions.DisplayDescriptionDataDataCallback;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.repository.DataPathName;

public class RepositoryBaseItem extends Composite implements HasText {

	private static RepositoryBaseItemUiBinder uiBinder = GWT.create(RepositoryBaseItemUiBinder.class);

	interface RepositoryBaseItemUiBinder extends UiBinder<Widget, RepositoryBaseItem> {
	}
	String restObjectsS = "...";
	/**
	 * Instantiates a new query string set.
	 */

	/** The stringlabel. */
	@UiField
	MaterialLink stringlabel;
	
	/** The stringpanel. */
	@UiField
	HTMLPanel setpanel;
	
	/** The setcollapse. */
	@UiField
	MaterialCollapsible setcollapse;
	
	/** The remove. */
	@UiField
	MaterialLink remove;
	/** The add. */
	@UiField
	MaterialLink add;
	/** The expand. */
	@UiField
	MaterialLink expand;
	
	/** The parent. */
	MaterialCollapsible parent;
	
	/** The objects displayed. */
	Set<String> subItems;

	HashMap<String, RepositoryBaseItem> directory;
	
	RepositoryPath topPath;

	public RepositoryBaseItem() {
		initWidget(uiBinder.createAndBindUi(this));
		init("Repository");
	}
	public RepositoryBaseItem(String top) {
		initWidget(uiBinder.createAndBindUi(this));
		init(top);
	}
	public void init(String name) {
		subItems = new HashSet<String>();
		topPath = new RepositoryPath();
		directory = new HashMap<String, RepositoryBaseItem>();
		parent = null;
		initialSettings();
		expand.setIconType(IconType.EXPAND_MORE);
		setText(name);
	}
	
	public RepositoryBaseItem(String stringKey, RepositoryPath topPath, MaterialCollapsible parent) {
		initWidget(uiBinder.createAndBindUi(this));
		init(stringKey);
		this.parent = parent;
		this.topPath = topPath;
	}

	public RepositoryBaseItem addSubItemLabel(String label) {
		RepositoryPath newpath = new RepositoryPath(topPath,label);
		RepositoryBaseItem subitem = new RepositoryBaseItem(label, newpath, setcollapse);
		subitem.initialSettings();
		setcollapse.add(subitem);
		return subitem;
	}
	public RepositoryBaseItem getSubItem(String label) {
		int cnt = setcollapse.getWidgetCount();
		RepositoryBaseItem subitem = null;
		int i=0;
		while(i < cnt && subitem == null) {
			Widget widget = setcollapse.getWidget(i);
			String name = widget.getClass().getName();
			if(name.matches(RepositoryBaseItem.class.getName())) {
				RepositoryBaseItem item = (RepositoryBaseItem) widget;
				if(item.getText().matches(label)) {
					subitem = item;
				}
			}
			i++;
		}
		return subitem;
	}
	
	public void initialSettings() {
		expand.setVisible(false);
		add.setVisible(false);
		remove.setVisible(false);
	}
	public void leaveSettings() {
		expand.setVisible(true);
		add.setVisible(false);
		remove.setVisible(false);
	}
	public void expandedSettings() {
		expand.setVisible(false);
		add.setVisible(true);
		remove.setVisible(false);
	}
	public void lastNodeSettings() {
		remove.setVisible(true);
	}
	public void setText(String text) {
		stringlabel.setText(text);
	}

	@UiHandler("expand")
	void onExpand(ClickEvent event) {
		DataPathName dataname = new DataPathName(topPath);
		DisplayDescriptionDataDataCallback callback = new DisplayDescriptionDataDataCallback(this);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getDescriptionDataData(dataname.getSourceKey(),dataname.getKeyword(), callback);
	}
	
	public void addDescriptionDataData(DataDescription data) {
		setcollapse.add(data);
		lastNodeSettings();
	}
	public String getText() {
		return stringlabel.getText();
	}
	public RepositoryPath getTopPath() {
		return topPath;
	}
}
