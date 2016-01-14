package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;

import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;

import com.google.gwt.core.shared.GWT;

public enum InputSet {
	organization {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.chemkintitle());
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
		
	},
	chemkin {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.chemkintitle());
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
	},
	sdfmolecules {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.sdfmoleculetitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfmolecules.getWidget());
			inputset.add(InputPanel.nasapolynomials.getWidget());
			return inputset;
		}
		
	},
	sdfsubstructures {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.sdfmoleculetitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.sdfsubstructures.getWidget());
			return inputset;
		}
		
	},
	thergasmolecules {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.thergasmoleculestitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			inputset.add(InputPanel.thergasmolecules.getWidget());
			return inputset;
		}
		
	},
	nasapolynomials {
		@Override
		public DataDescription getDescription() {

			DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
			DataDescription description = new DataDescription(
					descriptionConstants.thergasmoleculestitle());
			return description;
		}

		@Override
		public ArrayList<DataInput> getSet() {
			ArrayList<DataInput> inputset = new ArrayList<DataInput>();
			InputPanel panel = InputPanel.nasapolynomials;
			inputset.add(panel.getWidget());
			return inputset;
		}
		
	};


	public abstract DataDescription getDescription();

	public abstract ArrayList<DataInput> getSet();

}
