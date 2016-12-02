package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.core.client.GWT;
import info.esblurock.reaction.client.resources.ProcessDescriptions;

public enum ProcessDescriptionsForInterface {
	
	
	RegisterDataDescription {

		@Override
		public String getDescription() {
			return description.RegisterDataDescription();
		}
	}, RegisterDataSetReferences {

		@Override
		public String getDescription() {
			return description.RegisterDataSetReferences();
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
	}, ReadThergasMoleculesFile {

		@Override
		public String getDescription() {
			return description.ReadThergasMoleculesFile();
		}
		
	}, ReadReactSDFMolecules {

		@Override
		public String getDescription() {
			return description.ReadReactSDFMolecules();
		}
		
	}, ReadReactMolCorrespondencesFile {

		@Override
		public String getDescription() {
			return description.ReadReactMolCorrespondencesFile();
		}
		
	}
	, ValidateTransportFile {
		@Override
		public String getDescription() {
			return description.ValidatedTransportPropertiesFile();
		}
	}, ValidateChemkinMechanismFile {

		@Override
		public String getDescription() {
			return description.ValidateChemkinMechanismFile();
		}
		 
	}, ValidateNASAPolynomialFile {
		@Override
		public String getDescription() {
			return description.ValidateNASAPolynomialFile();
		}
	}, ValidateThergasMoleculesFile {

		@Override
		public String getDescription() {
			return description.ValidateThergasMoleculesFile();
		}
		
	}, ValidateReactSDFMolecules {

		@Override
		public String getDescription() {
			return description.ValidateReactSDFMolecules();
		}
		
	}, ValidateReactMolCorrespondences {

		@Override
		public String getDescription() {
			return description.ValidateReactMolCorrespondences();
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
		
	}, ThergasMoleculeToDatabase {

		@Override
		public String getDescription() {
			return description.ThergasMoleculeToDatabase();
		}
		
	}, ReactSDFMoleculesToDatabase {

		@Override
		public String getDescription() {
			return description.ReactSDFMoleculesToDatabase();
		}
		
	}, ReactMolCorrespondencesToDatabase {

		@Override
		public String getDescription() {
			return description.ReactMolCorrespondencesToDatabase();
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
	}, ThergasMoleculeProcessRDF {
		@Override
		public String getDescription() {
			return description.ThergasMoleculeProcessRDF();
		}
		
	}, ReactSDFMoleculesProcessRDF {

		@Override
		public String getDescription() {
			return description.ReactSDFMoleculesProcessRDF();
		}
		
	}, ReactMoleCorrespondencesProcessRDF {

		@Override
		public String getDescription() {
			return description.ReactMoleCorrespondencesProcessRDF();
		}
		
	};
	
	ProcessDescriptions description = GWT.create(ProcessDescriptions.class);

	public abstract String getDescription();
}
