package info.esblurock.reaction.client.panel.data;

import java.util.ArrayList;
import java.util.Collections;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
public enum DataPresentation {
	MechanismMoleculeData {

		@Override
		public
		String asOnLine(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;
			
			String out = molecule.getMoleculeName() + "(" + molecule.getMechanismKeyword() + ")";
			return out;
		}

		@Override
		public
		String multiLineString(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;
			String out = molecule.getMoleculeName() + "(" + molecule.getMechanismKeyword() + ")";
			return out;
		}

		@Override
		public
		BaseDataPresentation asDisplayObject(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;
			String title = asOnLine(data);
			BaseDataPresentation present = new BaseDataPresentation(title);
			return present;
		}
		
	},
	ChemkinReactionData {

		@Override
		public
		String asOnLine(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			
			StringBuilder build = new StringBuilder();
			build.append(getReactionName(reaction.getReactantKeys(), reaction.getProductKeys()));
			
			build.append("   Coefs: ");
			if(reaction.getForwardCoefficients() != null)
				build.append("forward ");
			if(reaction.getReverseCoefficients() != null)
				build.append("reverse ");
			if(reaction.getTroeCoefficients() != null)
				build.append("troe ");
			if(reaction.getThirdBodyMolecules() != null)
				build.append("3rd Body ");

			return build.toString();
		}

		@Override
		public
		String multiLineString(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			StringBuilder build = new StringBuilder();
			build.append(getReactionName(reaction.getReactantKeys(), reaction.getProductKeys()));

			build.append("   Coefficients:\n");
			if(reaction.getForwardCoefficients() != null) 
				build.append("forward ");
			if(reaction.getReverseCoefficients() != null)
				build.append("reverse ");
			if(reaction.getTroeCoefficients() != null)
				build.append("troe ");
			if(reaction.getThirdBodyMolecules() != null)
				build.append("3rd Body ");

			return build.toString();
		}

		@Override
		public
		BaseDataPresentation asDisplayObject(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;

			StringBuilder build = new StringBuilder();
			String title = asOnLine(data);
			BaseDataPresentation present = new BaseDataPresentation(title);
			return present;
		}
		
	};
	
	public String getReactionName(ArrayList<String> reactants,ArrayList<String> products) {
		String reacS = getMoleculeListString(reactants);
		String prodS = getMoleculeListString(products);
		String rxnS = reacS + "=" + prodS;
		return rxnS;
	}
	
	public String getMoleculeListString(ArrayList<String> mol) {
		Collections.sort(mol);
		StringBuilder build = new StringBuilder();
		for(String name : mol) {
			build.append(name);
			build.append("+");
		}
		String appendname = build.toString();
		String endmol = appendname.substring(0, appendname.length()-2);
		return endmol;
	}

	
	
	
	abstract public String asOnLine(DatabaseObject data);
	abstract public String multiLineString(DatabaseObject data);
	abstract public BaseDataPresentation asDisplayObject(DatabaseObject data);
}
