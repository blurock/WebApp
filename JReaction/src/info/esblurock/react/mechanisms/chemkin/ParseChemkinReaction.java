package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.react.common.PatternRecognizer;

public class ParseChemkinReaction {
	private ArrayList<ChemkinMolecule> Reactants = new ArrayList<ChemkinMolecule>();
	private ArrayList<ChemkinMolecule> Products = new ArrayList<ChemkinMolecule>();

	private String reactantsAsString;
	private String productsAsString;

	private String commentChar = "!";
	private String hvS = "hv";
	PatternRecognizer recognizer;

	private String comment;
	private boolean ThirdBodyFlag;
	private ReactionForwardReverseType rtype;

	public ParseChemkinReaction() {
		recognizer = new PatternRecognizer();
		ThirdBodyFlag = false;
	}

	public String normalize(String rxn) {
		String ans = rxn;
		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
		rtype = types.findReactionType(rxn);
		System.out.println("ReactionForwardReverseType:normalize: '" + rtype + "'");
		if (rtype != null) {
			String react = reduce(types.getReactants());
			String prod = reduce(types.getProducts());
			ans = react.trim() + rtype.getEquivString() + prod.trim();
		}
		return ans;
	}

	private String reduce(String rxn) {
		String rxn1 = plusSpace(rxn);
		return spacePlus(rxn1);
	}

	private String plusSpace(String rxn) {
		String reduced = rxn;
		boolean notdone = true;
		while (notdone) {
			int pos = reduced.indexOf("+ ");
			if (pos > 0) {
				reduced = reduced.substring(0, pos) + "+" + reduced.substring(pos + 2);
			} else {
				notdone = false;
			}
		}
		return reduced;
	}

	private String spacePlus(String rxn) {
		String reduced = rxn;
		boolean notdone = true;
		while (notdone) {
			int pos = reduced.indexOf(" +");
			if (pos > 0) {
				reduced = reduced.substring(0, pos) + "+" + reduced.substring(pos + 2);
			} else {
				notdone = false;
			}
		}
		return reduced;
	}

	public StringTokenizer parse(String rxn, boolean withCoefficients) throws IOException {
		int pos = rxn.indexOf(commentChar);
		String next;
		if (pos > 0) {
			next = rxn.substring(0, pos);
			comment = rxn.substring(pos + 1);
		} else {
			next = rxn;
			comment = null;
		}
		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
		types.findReactionType(next);
		reactantsAsString = types.getReactants();
		parseReactants(reactantsAsString, withCoefficients);
		productsAsString = types.getProducts();
		StringTokenizer tok = parseProducts(productsAsString, withCoefficients);
		return tok;
	}

	private void parseReactants(String react, boolean withCoefficients) throws IOException {
		parseMolList(react, true, withCoefficients);
	}

	private StringTokenizer parseProducts(String react, boolean withCoefficients) throws IOException {
		StringTokenizer tok;
		tok = parseMolList(react, false, withCoefficients);
		return tok;
	}

	private StringTokenizer parseMolList(String mollist, boolean reactants, boolean withCoefficients)
			throws IOException {
		StringTokenizer tok = new StringTokenizer(mollist, " ");
		int num = tok.countTokens();
		if (!reactants && withCoefficients)
			num = num - 3;
		String mols = "";
		while (num > 0) {
			String piece = tok.nextToken();
			mols += piece;
			num--;
		}
		if (reactants)
			reactantsAsString = mols;
		else
			productsAsString = mols;
		StringTokenizer tok1 = new StringTokenizer(mols, "+");
		while (tok1.hasMoreTokens()) {
			String molS = tok1.nextToken();
			if (molS.endsWith("(")) {
				tok1.nextToken();
				molS = molS.substring(0, molS.length() - 1);
				ThirdBodyFlag = true;
			}
			if (molS.equals("M") || molS.equals("m")) {
				ThirdBodyFlag = true;
			} else {
				int dupcnt = 1;
				ChemkinMolecule mol = new ChemkinMolecule(molS.trim());
				if (recognizer.numberFollowedByCharacter(molS)) {
					int pos = recognizer.positionOfFirstLetter(molS);

					mol = new ChemkinMolecule(molS.substring(pos).trim());
					String dupS = molS.substring(0, pos);
					Integer dupI = new Integer(dupS);
					dupcnt = dupI.intValue();
				}

				if (mol.getLabel().equals(hvS)) {

				} else {
					while (dupcnt > 0) {
						if (reactants)
							Reactants.add(mol);
						else
							Products.add(mol);
						dupcnt--;
					}

				}
			}
		}
		return tok;
	}

	boolean isThirdBody(String mol) {
		return mol.indexOf("(+") > 0;
	}

	public ArrayList<ChemkinMolecule> getReactants() {
		return Reactants;
	}

	public ArrayList<ChemkinMolecule> getProducts() {
		return Products;
	}

	public String getReactantsAsString() {
		return reactantsAsString;
	}

	public String getProductsAsString() {
		return productsAsString;
	}

	public String getComment() {
		return comment;
	}

	public boolean isThirdBodyFlag() {
		return ThirdBodyFlag;
	}

	public ReactionForwardReverseType getReactiontype() {
		return rtype;
	}

}
