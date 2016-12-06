package info.esblurock.reaction.client.panel.data.reaction;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;

public class FillInCoefficientsCallback implements AsyncCallback<ArrayList<ChemkinCoefficientsData>>{

	MaterialCollapsible coefficientsspanel;
	
	public FillInCoefficientsCallback(MaterialCollapsible coefficientsspanel) {
		this.coefficientsspanel = coefficientsspanel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(ArrayList<ChemkinCoefficientsData> result) {
		for(ChemkinCoefficientsData data : result) {
			FormatChemkinCoefficientsData format = new FormatChemkinCoefficientsData(data);
			MaterialCollapsibleItem item = new MaterialCollapsibleItem();
			coefficientsspanel.add(item);
			MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
			item.add(header);
			MaterialLabel label = new MaterialLabel();
			label.setText(format.getType(data));
			header.add(label);
			MaterialCollapsibleBody body = new MaterialCollapsibleBody();
			item.add(body);
			body.add(format);
			
			
		}
	}

}
