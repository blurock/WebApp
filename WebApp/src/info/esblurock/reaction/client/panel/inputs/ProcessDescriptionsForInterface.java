package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.core.client.GWT;
import info.esblurock.reaction.client.resources.ProcessDescriptions;

public enum ProcessDescriptionsForInterface {
	
	
	RegisterDataDescription {

		@Override
		public String getDescription() {
			return description.RegisterDataDescription();
		}
	}, ReadChemkinMechanismFile {

		@Override
		public String getDescription() {
			return description.ReadChemkinMechanismFile();
		}
	}, ReadNASAPolynomialFile {
		@Override
		public String getDescription() {
			return description.ReadNASAPolynomialFile();
		}
	}, ReadTransportPropertiesFile {
		@Override
		public String getDescription() {
			return description.ReadTransportPropertiesFile();
		}
	}, MechanismMoleculesToDatabase {
		@Override
		public String getDescription() {
			return description.MechanismMoleculesToDatabase();
		}
	},MechanismReactionsToDatabase {
		@Override
		public String getDescription() {
			return description.MechanismReactionsToDatabase();
		}
	}, NASAPolynomialsToDatabase {
		@Override
		public String getDescription() {
			return description.NASAPolynomialsToDatabase();
		}
	}, TransportPropertiesToDatabase {
		@Override
		public String getDescription() {
			return description.TransportPropertiesToDatabase();
		}
	},MechanismMoleculeProcessRDF {
		@Override
		public String getDescription() {
			return description.MechanismMoleculeProcessRDF();
		}
		
	}, MechanismReactionsProcessRDF {
		@Override
		public String getDescription() {
			return description.MechanismReactionsProcessRDF();
		}
	}, NASAPolynomialProcessRDF {
		@Override
		public String getDescription() {
			return description.NASAPolynomialProcessRDF();
		}
	}, TransportPropertiesProcessRDF {
		@Override
		public String getDescription() {
			return description.TransportPropertiesProcessRDF();
		}
	};
	ProcessDescriptions description = GWT.create(ProcessDescriptions.class);

	public abstract String getDescription();
}
