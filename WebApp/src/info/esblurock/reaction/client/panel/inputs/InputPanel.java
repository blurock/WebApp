package info.esblurock.reaction.client.panel.inputs;

import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.panel.inputs.resource.PanelInputResources;
import info.esblurock.reaction.client.resources.InputConstants;

import com.google.gwt.core.shared.GWT;

public enum InputPanel {

	chemkinReactions {
		@Override
		
		public DataInput getWidget(DataDescription description) {
			InputConstants inputConstants = GWT.create(InputConstants.class);
			String text = PanelInputResources.INSTANCE.exampleChemkinMechanismReactions().getText();
			String specName = "info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification";
			String transName = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
			DataInput mechanism = new DataInput(
					specName,
					transName,
					description,
					inputConstants.chemkinmechanismtype(),
					inputConstants.chemkinmechanismtitle(),
					inputConstants.chemkinmechanismhtitletooltip(),
					inputConstants.chemkinmechanismhttptext(),
					inputConstants.chemkinmechanismtexttooltip(),
					text);
			return mechanism;
		}
	},
	nasapolynomials {
		@Override
		public DataInput getWidget(DataDescription description) {

			InputConstants inputConstants = GWT.create(InputConstants.class);
			String specName = "info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification";
			String transName = "info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload";

			DataInput mechanism = new DataInput(
					specName,
					transName,
					description,
					inputConstants.nasapolynomialstype(),
					inputConstants.nasapolynomialstitle(),
					inputConstants.nasapolynomialstitletooltip(),
					inputConstants.nasapolynomialshttptext(),
					inputConstants.nasapolynomialstexttooltip(),
					PanelInputResources.INSTANCE.exampleNASAPolynomialSet().getText());
			return mechanism;
		};
	},
	transport {
		@Override
		public DataInput getWidget(DataDescription description) {

			InputConstants inputConstants = GWT.create(InputConstants.class);
			String specName = "info.esblurock.reaction.data.upload.TransportFileSpecification";
			String transName = "info.esblurock.reaction.data.upload.types.TransportFileUpload";
			DataInput mechanism = new DataInput(
					specName,
					transName,
					description,
					inputConstants.transporttype(),
					inputConstants.transporttitle(),
					inputConstants.transporttitletooltip(),
					inputConstants.transporthttptext(),
					inputConstants.transporttexttooltip(),
					PanelInputResources.INSTANCE.exampleTransportSet().getText());
			return mechanism;
		};
	},
	sdfsubstructures {
		@Override
		public DataInput getWidget(DataDescription description) {

			InputConstants inputConstants = GWT.create(InputConstants.class);
			String specName = "info.esblurock.reaction.data.upload.";
			String transName = "info.esblurock.reaction.data.upload.";
			DataInput mechanism = new DataInput(
					specName,
					transName,
					description,
					inputConstants.sdfsubstructurestype(),
					inputConstants.sdfsubstructurestitle(),
					inputConstants.sdfsubstructurestitletooltip(),
					inputConstants.sdfsubstructureshttptext(),
					inputConstants.sdfsubstructurestexttooltip(),
					PanelInputResources.INSTANCE.exampleSDFSubstructureSet().getText());
			return mechanism;
		};
	},
	sdfmolecules {
		@Override
		public DataInput getWidget(DataDescription description) {

			InputConstants inputConstants = GWT.create(InputConstants.class);
			String specName = "info.esblurock.reaction.data.upload.";
			String transName = "info.esblurock.reaction.data.upload.";
			DataInput mechanism = new DataInput(
					specName,
					transName,
					description,
					inputConstants.sdfmoleculetype(),
					inputConstants.sdfmoleculehttptext(),
					inputConstants.sdfmoleculetitletooltip(),
					inputConstants.sdfmoleculehttptext(),
					inputConstants.sdfmoleculetexttooltip(),
					PanelInputResources.INSTANCE.exampleSDFMoleculeSet().getText());
			return mechanism;
		};
	},
	thergasmolecules {
		@Override
		public DataInput getWidget(DataDescription description) {

		InputConstants inputConstants = GWT.create(InputConstants.class);
		String specName = "info.esblurock.reaction.data.upload.";
		String transName = "info.esblurock.reaction.data.upload.";
		DataInput mechanism = new DataInput(
				specName,
				transName,
				description,
				inputConstants.thergasmoleculetype(),
				inputConstants.thergasmoleculestitle(),
				inputConstants.thergasmoleculestitletooltip(),
				inputConstants.thergasmoleculeshttptext(),
				inputConstants.thergasmoleculestexttooltip(),
				PanelInputResources.INSTANCE.exampleThergasMoleculeSet().getText());
		return mechanism;
	};
};

	private boolean required = true;
	public void setRequired(boolean r){
		required = r;
	}
	public boolean getRequired() {
		return required;
	}
	public abstract DataInput getWidget(DataDescription description);
}
