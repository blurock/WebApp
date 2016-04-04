package info.esblurock.reaction.client.panel.data.reaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.client.panel.data.DataPresentation;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;

public class FormatChemkinReactionData extends Composite implements HasText {

	private static FormatChemkinReactionDataUiBinder uiBinder = GWT.create(FormatChemkinReactionDataUiBinder.class);

	interface FormatChemkinReactionDataUiBinder extends UiBinder<Widget, FormatChemkinReactionData> {
	}

	public FormatChemkinReactionData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HTMLPanel mainPanel;

	public FormatChemkinReactionData(ChemkinReactionData reaction) {
		initWidget(uiBinder.createAndBindUi(this));
		if (reaction.getForwardCoefficients() != null) {
			FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(reaction.getForwardCoefficients());
			coeffs.addSaveButton();
			mainPanel.add(coeffs);
		}
		if (reaction.getReverseCoefficients() != null) {
			FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(reaction.getReverseCoefficients());
			coeffs.addSaveButton();
			mainPanel.add(coeffs);
		}
			
		if (reaction.getTroeCoefficients() != null) {
			FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(reaction.getTroeCoefficients());
			coeffs.addSaveButton();
			mainPanel.add(coeffs);
		}
			
		if (reaction.getHighCoefficients() != null) {
			FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(reaction.getHighCoefficients());
			coeffs.addSaveButton();
			mainPanel.add(coeffs);
		}
		if (reaction.getSriCoefficients() != null) {
			FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(reaction.getSriCoefficients());
			mainPanel.add(coeffs);
		}
			
		if (reaction.getPlogCoefficients() != null) {
			DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
			for(ChemkinCoefficientsData c: reaction.getPlogCoefficients()) {
				FormatChemkinCoefficientsData coeffs = new FormatChemkinCoefficientsData(c);
				coeffs.addSaveButton();
				mainPanel.add(coeffs);
			}
										
		}
		
	}


	public void setText(String text) {
		
	}

	public String getText() {
		return null;
	}

}
