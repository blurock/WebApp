package info.esblurock.reaction.client.panel.data.thermo;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;

import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;

public class NASAThermoPanel extends Composite implements HasText {

	private static NASAThermoPanelUiBinder uiBinder = GWT.create(NASAThermoPanelUiBinder.class);

	interface NASAThermoPanelUiBinder extends UiBinder<Widget, NASAThermoPanel> {
	}

	public NASAThermoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField
	MaterialLabel lower1,lower2,lower3,lower4,lower5,lower6,lower7;
	@UiField
	MaterialLabel upper1,upper2,upper3,upper4,upper5,upper6,upper7;
	@UiField
	MaterialLabel line1,line2,line3,line4;

	@UiField
	MaterialButton calculateEnthalpy;
	@UiField
	MaterialButton calculateEntropy;
	
	@UiField
	MaterialLabel lowerLabel, upperLabel;
	
	@UiField
	MaterialLabel enthalpy, ethalpyLabel, entropy, entropyLabel;
	
	public NASAThermoPanel(NASAPolynomialData nasa) {
		initWidget(uiBinder.createAndBindUi(this));
		ArrayList<Double> lc = nasa.getLower();
		ArrayList<Double> uc = nasa.getUpper();
		
		FormatNASAPolynomialData formatter = new FormatNASAPolynomialData();
		formatter.convertNASAPolynomial(nasa);
		
		enthalpy.setText(nasa.getStandardEnthalpy().toString());
		entropy.setText(nasa.getStandardEntropy().toString());
		
		
		coefficientFormat(lower1,lc.get(0));
		coefficientFormat(lower2,lc.get(1));
		coefficientFormat(lower3,lc.get(2));
		coefficientFormat(lower4,lc.get(3));
		coefficientFormat(lower5,lc.get(4));
		coefficientFormat(lower6,lc.get(5));
		coefficientFormat(lower7,lc.get(6));
		
		coefficientFormat(upper1,uc.get(0));
		coefficientFormat(upper2,uc.get(1));
		coefficientFormat(upper3,uc.get(2));
		coefficientFormat(upper4,uc.get(3));
		coefficientFormat(upper5,uc.get(4));
		coefficientFormat(upper6,uc.get(5));
		coefficientFormat(upper7,uc.get(6));
		
		
		line1.setText(formatter.getLine1());
		line2.setText(formatter.getLine2());
		line3.setText(formatter.getLine3());
		line4.setText(formatter.getLine4());
		
	}

	void coefficientFormat(MaterialLabel lbl, Double num) {
		lbl.getElement().getStyle().setFontSize(0.8, Unit.EM);
		lbl.setText(NumberFormat.getScientificFormat().overrideFractionDigits(4).format(num.doubleValue()));
	}
	
	@UiHandler("calculateEnthalpy")
	void onEthaplyClick(ClickEvent e) {
		Window.alert("Calculate Entalpy at given temperature");
	}
	@UiHandler("calculateEntropy")
	void onEntropyClick(ClickEvent e) {
		Window.alert("Calculate Entropy at given temperature");
	}

	public void setText(String text) {
		
	}

	public String getText() {
		return null;
	}

}
