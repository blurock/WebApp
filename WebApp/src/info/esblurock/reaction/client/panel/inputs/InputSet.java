package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;

import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.server.process.ProcessBase;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;

public enum InputSet {
	organization {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.chemkintitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();

			inputset.add(InputPanel.chemkinReactions.getWidget(description));
			inputset.add(InputPanel.nasapolynomials.getWidget(description));
			DataInput transport = InputPanel.transport.getWidget(description);
			transport.setRequiredInput(false);
			inputset.add(transport);

			return inputset;
		}

		@Override
		public String getDataType() {
			return "Organization";
		}

	},
	chemkin {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.chemkintitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.chemkinReactions.getWidget(description));
			inputset.add(InputPanel.nasapolynomials.getWidget(description));
			DataInput transport = InputPanel.transport.getWidget(description);
			transport.setRequiredInput(false);
			inputset.add(transport);

			return inputset;
		}

		@Override
		public String getDataType() {
			return "Chemkin";
		}
	},
	thergasmolecules {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.thergasmoleculestitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.thergasmolecules.getWidget(description));
			return inputset;
		}

		@Override
		public String getDataType() {
			return "ThergasMolecules";
		}

	},
	sdfmolecules {
		@Override
		public DataDescription getDescription() {
			MaterialToast.fireToast("sdfmolecules: getDescription()");

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.sdfmoleculetitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			MaterialToast.fireToast("sdfmolecules: getSet(DataDescription description)");
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfmolecules.getWidget(description));
			inputset.add(InputPanel.reactmolcorrs.getWidget(description));
			inputset.add(InputPanel.nasapolynomials.getWidget(description));
			return inputset;
		}

		@Override
		public String getDataType() {
			return "Reaction2DMolecules";
		}

	},
	sdfsubstructures {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.sdfmoleculetitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfsubstructures.getWidget(description));
			return inputset;
		}

		@Override
		public String getDataType() {
			return "Reaction2DMolecules";
		}

	},
	nasapolynomials {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.thergasmoleculestitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			InputPanel panel = InputPanel.nasapolynomials;
			inputset.add(panel.getWidget(description));
			return inputset;
		}

		@Override
		public String getDataType() {
			return "NASAPolynomials";
		}

	};

	public abstract DataDescription getDescription();

	public abstract ArrayList<DataInput> getSet(DataDescription description);

	public abstract String getDataType();
}
