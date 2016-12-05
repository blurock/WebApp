package info.esblurock.reaction.client.panel.data.reaction;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;

public class ReactionMoleculeListPanel extends Composite implements HasText {

	private static ReactionMoleculeListPanelUiBinder uiBinder = GWT.create(ReactionMoleculeListPanelUiBinder.class);

	interface ReactionMoleculeListPanelUiBinder extends UiBinder<Widget, ReactionMoleculeListPanel> {
	}

	@UiField
	MaterialLabel moleculelisttype;
	@UiField
	HTMLPanel moleculepanel;

	public ReactionMoleculeListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ReactionMoleculeListPanel(String listname, ArrayList<String> molnames) {
		initWidget(uiBinder.createAndBindUi(this));
		moleculelisttype.setText(listname);
		for (String name : molnames) {
			addMolecule(name);
		}
	}

	public ReactionMoleculeListPanel(String listname, ArrayList<String> molnames, ArrayList<Double> weights) {
		initWidget(uiBinder.createAndBindUi(this));
		moleculelisttype.setText(listname);
		if (molnames != null && weights != null) {
			if (molnames.size() == weights.size()) {
				Iterator<Double> iter = weights.iterator();
				for (String name : molnames) {
					addMolecule(name, iter.next());
				}
			}
		}
	}

	private void addMolecule(String name, Double weight) {
		MaterialRow molrow = new MaterialRow();
		MaterialColumn molcol = new MaterialColumn();
		molcol.setGrid("s6");
		molrow.add(molcol);
		MaterialColumn wcol = new MaterialColumn();
		wcol.setGrid("s6");
		molrow.add(wcol);
		MaterialLink mollink = new MaterialLink(IconType.INFO);
		mollink.setText(name);
		molcol.add(mollink);
		MaterialLink wlink = new MaterialLink();
		wlink.setText(weight.toString());
		wcol.add(wlink);
		moleculepanel.add(molrow);
	}

	private void addMolecule(String moleculename) {
		MaterialRow molrow = new MaterialRow();
		MaterialColumn molcol = new MaterialColumn();
		molrow.add(molcol);
		MaterialLink mollink = new MaterialLink(IconType.INFO);
		mollink.setText(moleculename);
		molcol.add(mollink);
		moleculepanel.add(molrow);
	}

	public void setText(String text) {
		moleculelisttype.setText(text);
	}

	public String getText() {
		return moleculelisttype.getText();
	}

}
