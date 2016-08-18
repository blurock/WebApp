package info.esblurock.reaction.data.chemical.reaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ParseChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ReactionForwardReverseType;
import info.esblurock.react.mechanisms.chemkin.SetOfReactionForwardReverseTypes;
import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.client.GenerateReactionKeywords;

/**
 * A reaction is a set of reactant labels separated by plus followed by 
 * symbol set (determined by whether reversible, etc) 
 * and then a set of products separated by plus.
 * The parsing is the name as reading a reaction (without coefficients) within a CHEMKIN file
 * using the normalize routine in {@link ParseChemkinReaction}.
 * 
 * The reaction input (before normalization) can have spaces within the reaction specification.
 * Normalized form eliminates the spaces 
 * @author edwardblurock
 *
 */
public class CanonicalReactionName {
	String mechanismName;

/** Create with mechanism name
 * 
 * @param mechname The name of the mechanism where the reaction appears
 */
	public CanonicalReactionName(String mechname) {
		mechanismName = mechname;
	}

/** Empty constructor.
 * The mechanism name is set to null (not used).
 * 
 */
	public CanonicalReactionName() {
		mechanismName = null;
	}

/** This parses a reaction and converts it to normalized form.
 * 
 * Normalized for just eliminates the spaces in the reaction specification.
 * 
 * if there is other keywords before or after the reaction, they are left with spaces.
 * 
 * @param input The reaction string
 * @return The reaction in normalized form (no spaces)
 */
	public String getNormalizedReaction(String input) {
		ParseChemkinReaction parse = new ParseChemkinReaction();
		return parse.normalize(input);
	}

/** reactions and keywords blended together.
 * 
 * First the reaction part is normalized so that all reaction expressions have no spaces.
 * If a mechanism name (when a delimitor is detected) is followed by a reaction, then they
 * are concatenated to one name.
 * 
 * The result is a set of keywords, reactions (with or without mechanism prefix) and mechanism names.
 * 
 * @param input: The input string 
 * @param forward
 * @return
 * @throws IOException
 */
	public ParsedReactionInformation getCanonicalReactionName(String input) throws IOException {
		ParseChemkinReaction parse = new ParseChemkinReaction();
		String normed = parse.normalize(input);
		StringTokenizer tok = new StringTokenizer(normed, " ");
		
		ParsedReactionInformation parsed = new ParsedReactionInformation();
		String token = tok.nextToken();
		while(token != null) {
			if(token.contains(GenerateKeywords.delimitor)) {
				String next = nextToken(tok);
				if(next != null) {
					if(isReaction(next)) {
						addReaction(parsed, token, next);
						token = nextToken(tok);
					} else {
						parsed.addMechanismName(token);
						token = next;
					}
				} else {
					parsed.addMechanismName(token);
					token = nextToken(tok);
				}
			} else if(isReaction(token)) {
				addReaction(parsed, null, token);
				token = nextToken(tok);
			} else if(reactantOrProductList(token)) {
				parsed.addPlusList(token);
				token = nextToken(tok);
			} else {
				parsed.add(token);
				token = nextToken(tok);
			}
		}
		return parsed;
	}
	private boolean reactantOrProductList(String token) {
		return token.indexOf("+") > 0;
	}
	private void addReaction(ParsedReactionInformation parsed, String mechname, String token) throws IOException {
		String forward = getCanonicalName(token, mechname, true);
		String reverse = getCanonicalName(token, mechname, false);
		parsed.addForwardReaction(forward);
		parsed.addReverseReaction(reverse);		
	}
	private String nextToken(StringTokenizer tok) {
		String ans = null;
		if(tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}
	private boolean isReaction(String reactionS) {
		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
		ReactionForwardReverseType rtype = types.findReactionType(reactionS);
		boolean ans = rtype != null;
		return ans;
	}
	public String getCanonicalName(String rxn, String mechname, boolean forward) throws IOException {
		GenerateReactionKeywords generate = new GenerateReactionKeywords(mechname);
		String canonicalname = canonicalReactionName(rxn, forward);
		return generate.getReactionFullName(canonicalname);
	}

	public String canonicalReactionName(String rxn, boolean forward) throws IOException {
		ParseChemkinReaction parse = new ParseChemkinReaction();
		GenerateReactionKeywords generate = new GenerateReactionKeywords(mechanismName);
		String normalized = parse.normalize(rxn);
		parse.parse(normalized, false);
		ArrayList<String> reactantNames =  getMoleculeList(parse.getReactants());
		ArrayList<String> productNames = getMoleculeList(parse.getProducts());
		String canonicalname = null;
		if (forward) {
			canonicalname = generate.getReactionName(reactantNames, productNames);
		} else {
			canonicalname = generate.getReactionName(productNames,reactantNames);
		}
		return canonicalname;
	}

	private static ArrayList<String> getMoleculeList(ArrayList<ChemkinMolecule> mollist) {
		ArrayList<String> namelist = new ArrayList<String>();
		for (ChemkinMolecule mol : mollist) {
			namelist.add(mol.getLabel());
		}
		return namelist;
	}
}
