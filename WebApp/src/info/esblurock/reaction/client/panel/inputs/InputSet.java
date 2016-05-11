package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;

import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;

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
			return "ChemkinMechanism";
		}
	},
	sdfmolecules {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(descriptionConstants.sdfmoleculetitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet(DataDescription description) {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfmolecules.getWidget(description));
			inputset.add(InputPanel.nasapolynomials.getWidget(description));
			return inputset;
		}

		@Override
		public String getDataType() {
			return "2D-Molecules";
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
			return "2D-Substructures";
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
			return "ThermodynamicInfo";
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
