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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();

			inputset.add(InputPanel.chemkinReactions.getWidget());
			inputset.add(InputPanel.nasapolynomials.getWidget());
			DataInput transport = InputPanel.transport.getWidget();
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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();

			inputset.add(InputPanel.chemkinReactions.getWidget());
			inputset.add(InputPanel.nasapolynomials.getWidget());
			DataInput transport = InputPanel.transport.getWidget();
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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfmolecules.getWidget());
			inputset.add(InputPanel.nasapolynomials.getWidget());
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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfsubstructures.getWidget());
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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.thergasmolecules.getWidget());
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
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			InputPanel panel = InputPanel.nasapolynomials;
			inputset.add(panel.getWidget());
			return inputset;
		}

		@Override
		public String getDataType() {
			return "NASAPolynomials";
		}

	};

	public abstract DataDescription getDescription();

	public abstract ArrayList<DataInput> getSet();

	public abstract String getDataType();

}
