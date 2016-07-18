package info.esblurock.reaction.parse.objects.chemical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Query.Filter;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ParseChemkinReaction;
import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.client.GenerateReactionKeywords;

public class CanonicalReactionName {
	ParseChemkinReaction parse;
	GenerateReactionKeywords generate;

	public CanonicalReactionName(String mechname) {
		parse = new ParseChemkinReaction();
		generate = new GenerateReactionKeywords(mechname);
	}
	public CanonicalReactionName() {
		parse = new ParseChemkinReaction();
		generate = new GenerateReactionKeywords(null);
	}
	
	public String getNormalizedReaction(String input) {
		return parse.normalize(input);
	}
	
	public String getCanonicalReactionName(String input) throws IOException {
		String normed = getNormalizedReaction(input);
		StringTokenizer tok = new StringTokenizer(normed," ");
		String currentReaction = normed;
		String currentMechanismName = null;
		if(tok.countTokens() == 2) {
			String s1 = tok.nextToken();
			String s2 = tok.nextToken();
			if(s1.contains(GenerateKeywords.delimitor)) {
				currentReaction = s2;
				currentMechanismName = s1;
			} else {
				currentReaction = s1;
				currentMechanismName = s2;
			}
		}
		String ans = getCanonicalName(currentReaction,currentMechanismName);
		return ans;
	}
	
	public String getCanonicalName(String rxn,String mechname) throws IOException {
		String canonicalname = canonicalReactionName(rxn);
		return generate.getReactionFullName(canonicalname);
	}
	
	public String canonicalReactionName(String rxn) throws IOException {
		String normalized = parse.normalize(rxn);
		parse.parse(normalized, false);
		ArrayList<String> reactantNames = getMoleculeList(parse.getReactants());
		ArrayList<String> productNames = getMoleculeList(parse.getProducts());
		String canonicalname = generate.getReactionName(reactantNames,productNames);
		return canonicalname;
	}
	private static ArrayList<String> getMoleculeList(ArrayList<ChemkinMolecule> mollist) {
		ArrayList<String> namelist = new ArrayList<String>();
		for(ChemkinMolecule mol : mollist) {
			namelist.add(mol.getLabel());
		}
		return namelist;
	}
}
