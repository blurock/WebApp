package info.esblurock.reaction.data.rdf;

import info.esblurock.reaction.client.resources.RDFProperties;
import com.google.gwt.core.client.GWT;

public enum RDFPredicateDescriptions {

	Description {

		@Override
		public String getTranslation() {
			return null;
		}

		@Override
		public String getOneLineDescription() {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	MechanismMolecule {
		@Override
		public String getTranslation() {return rdf.MechanismMolecule();};
		@Override
		public String getOneLineDescription() {return rdf.MechanismMoleculeDescription();};
	},
	SpeciesName {
		@Override
		public String getTranslation() {return rdf.SpeciesName();};
		@Override
		public String getOneLineDescription() {return rdf.SpeciesNameDescription();};
	};
	
	RDFProperties rdf = GWT.create(RDFProperties.class);
	
	public abstract String getTranslation();
	public abstract String getOneLineDescription();
}
