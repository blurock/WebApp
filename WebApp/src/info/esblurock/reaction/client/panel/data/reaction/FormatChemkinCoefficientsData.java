package info.esblurock.reaction.client.panel.data.reaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;

public class FormatChemkinCoefficientsData extends Composite implements HasText {

	private static FormatChemkinCoefficientsDataUiBinder uiBinder = GWT
			.create(FormatChemkinCoefficientsDataUiBinder.class);

	interface FormatChemkinCoefficientsDataUiBinder extends UiBinder<Widget, FormatChemkinCoefficientsData> {
	}

	public FormatChemkinCoefficientsData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLabel coefficientType,ALabel,A,nLabel,n,EaLabel,Ea;
	
	@UiField
	MaterialRow arrheniusRow,coefficientsRow;
	
	ChemkinCoefficientsData coefficients;

	public FormatChemkinCoefficientsData(ChemkinCoefficientsData coeffs) {
		initWidget(uiBinder.createAndBindUi(this));
		this.coefficients = coeffs;
		String type = getType(coeffs);
		coefficientType.setText(type);
		loadArrheniusCoefficients(coeffs);
		loadCoefficients(coeffs);
	}

	void loadArrheniusCoefficients(ChemkinCoefficientsData coeffs) {
		if(coeffs.forward || coeffs.reverse || coeffs.high || coeffs.low) {
			arrheniusRow.setVisible(true);
		A.setText(coeffs.getA());
		n.setText(coeffs.getN());
		Ea.setText(coeffs.getEa());
		} else {
			arrheniusRow.setVisible(false);
		}
	}
	
	void loadCoefficients(ChemkinCoefficientsData coeffs) {
		if(coeffs.troe || coeffs.sri || coeffs.plog) {
			coefficientsRow.setVisible(true);
			int count = 0;
			for(String coeff : coeffs.getCoeffs()) {
				MaterialLabel lbl = new MaterialLabel();
				lbl.setText(coeff);
				MaterialColumn col = new MaterialColumn(2,2,2);
				col.add(lbl);
				count++;
				coefficientsRow.add(col);
			}
			
			
		} else {
			coefficientsRow.setVisible(false);
			
		}
	}
	
	String getType(ChemkinCoefficientsData coeffs) {
		String type = "";
		if(coeffs.forward) {
			type = "Forward";
		} else if(coeffs.reverse) {
			type = "Reverse";
		} else if(coeffs.low) {
			type = "Low";
		} else if(coeffs.high) {
			type = "High";
		} else if(coeffs.plog) {
			type = "PLOG";
		} else if(coeffs.sri) {
			type = "SRI";
		} else if(coeffs.troe) {
			type = "Troe";
		}
		return type;
	}
	public void setText(String text) {
		
	}

	public String getText() {
		return null;
	}

}
