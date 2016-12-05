package info.esblurock.reaction.client.panel.data.reaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;

public class ReactionDataPresentation extends Composite implements HasText {

	private static ReactionDataPresentationUiBinder uiBinder = GWT.create(ReactionDataPresentationUiBinder.class);

	interface ReactionDataPresentationUiBinder extends UiBinder<Widget, ReactionDataPresentation> {
	}

	public ReactionDataPresentation() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	DescriptionConstants descrconstants = GWT.create(DescriptionConstants.class);

	@UiField
	MaterialLabel mechanismkeywordlabel;
	@UiField
	MaterialLink mechanismkeyword;
	@UiField
	MaterialLink reactantslabel;
	@UiField
	HTMLPanel reactantspanel;
	@UiField
	MaterialLink productslabel;
	@UiField
	HTMLPanel productspanel;
	@UiField
	MaterialLink thirdbodylabel;
	@UiField
	HTMLPanel thirdbodypanel;
	@UiField
	MaterialLink coefficientslabel;
	@UiField
	MaterialCollapsible coefficientsspanel;
	
	ChemkinReactionData reaction;
	
	public ReactionDataPresentation(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		mechanismkeywordlabel.setText(firstName);
		reaction = null;
		init();
	}
	public ReactionDataPresentation(ChemkinReactionData reaction) {
		initWidget(uiBinder.createAndBindUi(this));
		mechanismkeyword.setText(reaction.getMechanismKeyword());
		this.reaction = reaction;
		init();
	}

	private void init() {
		mechanismkeywordlabel.setText(descrconstants.mechanismkeywordlabel());
		reactantslabel.setText(descrconstants.reactantslabel());
		productslabel.setText(descrconstants.productslabel());
		thirdbodylabel.setText(descrconstants.thirdbodymolecules());
		coefficientslabel.setText(descrconstants.coefficientslabel());
		if(reaction != null) {
			ReactionMoleculeListPanel reactants = new ReactionMoleculeListPanel(
					descrconstants.reactantslabel(),
					reaction.getReactantKeys());
			reactantspanel.add(reactants);
			ReactionMoleculeListPanel products = new ReactionMoleculeListPanel(
					descrconstants.productslabel(),
					reaction.getProductKeys());
			productspanel.add(products);
			ReactionMoleculeListPanel thirdbody = new ReactionMoleculeListPanel(
					descrconstants.productslabel(),
					reaction.getThirdBodyMoleculeLabels(),
					reaction.getThirdBodyMoleculeWeights());
			reactantspanel.add(thirdbody);
			fillInCoefficients(reaction.getReactionName());
		}
	}
	
	private void fillInCoefficients(String reactionName) {
		FillInCoefficientsCallback callback = new FillInCoefficientsCallback(coefficientsspanel);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.coefficientsFromReactionName(reactionName, callback);
	}
	public void setText(String text) {
		mechanismkeywordlabel.setText(text);
	}

	public String getText() {
		return mechanismkeywordlabel.getText();
	}

}
