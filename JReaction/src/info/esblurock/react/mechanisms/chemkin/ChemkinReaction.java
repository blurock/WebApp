package info.esblurock.react.mechanisms.chemkin;

import info.esblurock.react.common.PatternRecognizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 *
 * @author reaction
 */
public class ChemkinReaction {

	private ArrayList<ChemkinMolecule> Reactants = new ArrayList<ChemkinMolecule>();
	private ArrayList<ChemkinMolecule> Products = new ArrayList<ChemkinMolecule>();
	private ChemkinCoefficients forwardCoefficients;
	private ChemkinCoefficients reverseCoefficients;
	private ChemkinCoefficients lowCoefficients;
	private ChemkinCoefficients troeCoefficients;
	private ChemkinCoefficients highCoefficients;
	private ChemkinCoefficients sriCoefficients;
	private ArrayList<ChemkinCoefficients> plogCoefficients;
	private ThirdBodyMolecules thirdBodyMolecules;
	private boolean hvLight;
	private boolean hvLightAsReactant;

	private boolean ThirdBodyFlag = false;
	private boolean ThirdBodyCoeffsFlag = false;
	private boolean reversible = false;
	private boolean duplicate = false;
	private String comment = null;
	private boolean markedAsDuplicate = false;
	private String commentChar = "!";
	private String hvS = "hv";
	private String duplicateS = "DUP";

	private String reactantsAsString;
	private String productsAsString;

	ChemkinString lines;
	ChemkinMoleculeList molecules;

	PatternRecognizer recognizer = new PatternRecognizer();

	/** Creates a new instance of ChemkinReaction */
	public ChemkinReaction(ChemkinString ls, ChemkinMoleculeList mols) {
		lines = ls;
		molecules = mols;
		commentChar = ls.getCommentChar();
	}

	private void init() {
		ThirdBodyFlag = false;
		ThirdBodyCoeffsFlag = false;
		reversible = false;
		comment = null;
		markedAsDuplicate = false;
		commentChar = "!";
		lowCoefficients = null;
		forwardCoefficients = null;
		reverseCoefficients = null;
		lowCoefficients = null;
		troeCoefficients = null;
		plogCoefficients = null;
		thirdBodyMolecules = null;
		hvLight = false;
		hvLightAsReactant = false;

	}

	public String parse() throws IOException {
		init();
		String current = lines.currentNonBlank();
		String rxn = repairReactionDeclaration(current);
		int pos = rxn.indexOf(commentChar);

		String next;
		if (pos > 0) {
			next = rxn.substring(0, pos);
			comment = rxn.substring(pos + 1);
		} else {
			next = rxn;
			comment = null;
		}
		try {
			SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
			ReactionForwardReverseType rtype = types.findReactionType(next);
			// reactantsAsString = types.getReactants();
			parseReactants(types.getReactants());
			StringTokenizer tok = parseProducts(types.getProducts());
			forwardCoefficients = new ChemkinCoefficients(true);
			forwardCoefficients.parseCoeffs(tok);
			reversible = rtype.isReverse();
			if (ThirdBodyFlag) {
				next = parseThirdBodyCoeffs();
			} else if (lines.nextNonBlank() != null) {
				//String comments = lines.skipOverComments();
				lines.skipOverComments();
				boolean notdone = lines.currentNonBlank() != null;
				while (notdone) {
					ChemkinCoefficients reverse = new ChemkinCoefficients();
					String l = lines.currentNonBlank();
					if (reverse.parseReverse(l)) {
						reverseCoefficients = reverse;
						lines.nextNonBlank();
					} else if (reverse.parsePlog(l)) {
						reverse.setPlog(true);
						if (plogCoefficients == null) {
							plogCoefficients = new ArrayList<ChemkinCoefficients>();
						}
						while (reverse.parsePlog(l)) {
							l = lines.nextNonBlank();
							lines.skipOverComments();
							plogCoefficients.add(reverse);
							reverse = new ChemkinCoefficients();
						}
					} else if (l.trim().toUpperCase().startsWith(duplicateS)) {
						duplicate = true;
						lines.skipOverComments();
						l = lines.nextNonBlank();
						if (l == null)
							notdone = false;
					} else {
						notdone = false;
					}
				}
			}
		} catch (StringIndexOutOfBoundsException io) {
			throw new IOException("StringIndexOutOfBoundsException: " + next);
		}
		return lines.currentNonBlank();
	}

	private void parseReactants(String react) throws IOException {
		parseMolList(react, true);
	}

	private StringTokenizer parseProducts(String react) throws IOException {
		StringTokenizer tok;
		tok = parseMolList(react, false);
		return tok;
	}

	private StringTokenizer parseMolList(String mollist, boolean reactants) throws IOException {
		StringTokenizer tok = new StringTokenizer(mollist, " \t");
		int num = tok.countTokens();
		if (!reactants)
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
				molS += "+" + tok1.nextToken();
			}
			if (isThirdBody(molS)) {
				molS = processThirdBody(molS);
			}
			if (molS.equals("M") || molS.equals("m")) {
				ThirdBodyFlag = true;
			} else {
				boolean third = isolateThirdBody(molS,mollist,reactants);
				if(!third) {
					processMolecule(molS,mollist,reactants);
				} else {
					ThirdBodyFlag = true;
				}
			}
		}
		return tok;
	}

	private boolean isolateThirdBody(String molS, String mollist, boolean reactants) throws IOException {
		boolean third = false;
		String ans = "";
		int pos1 = molS.indexOf("(+");
		int pos2 = molS.indexOf(')');
		if(pos1 == 0) {
			ans = molS.substring(pos2+1);
			third = true;
		} if(pos1 > 0) {
			ans = molS.substring(0, pos2);
			third = true;
		}
		if(third) {
			processMolecule(ans,mollist,reactants);
		}
		return third;
	}
	private void processMolecule(String molS, String mollist, boolean reactants) throws IOException {
		int dupcnt = 1;
		ChemkinMolecule mol = new ChemkinMolecule(molS.trim());
		if (recognizer.numberFollowedByCharacter(molS)) {
			int pos = recognizer.positionOfFirstLetter(molS);

			mol = new ChemkinMolecule(molS.substring(pos).trim());
			String dupS = molS.substring(0, pos);
			Integer dupI = new Integer(dupS);
			dupcnt = dupI.intValue();
		}
		String molstring = mol.getLabel().toLowerCase();
		if (molstring.equals(hvS)) {
			hvLight = true;
			hvLightAsReactant = reactants;
		} else {
			if (!molecules.containsKey(mol.getLabel())) {
				molS = mol.getLabel();
				char ch[] = new char[molS.length()];
				int cnt = 0;
				int length = molS.length();
				while (!molecules.containsKey(mol.getLabel()) && cnt < length) {
					ch[cnt] = molS.charAt(0);
					cnt++;
					molS = molS.substring(1);
				}
				if (cnt < length) {
					String cntS = new String(ch, 0, cnt);
					try {
						dupcnt = Integer.parseInt(cntS);
					} catch (NumberFormatException ex) {
						throw new IOException("Multiplicity Error");
					}
				} else {
					throw new IOException("Species Name Error: '" + mol.getLabel() + "' from " + mollist);
				}
			}
			while (dupcnt > 0) {
				if (reactants)
					Reactants.add(mol);
				else
					Products.add(mol);
				dupcnt--;
			}

		}
		
	}
 	
	
	boolean isThirdBody(String mol) {
		return mol.indexOf("(+") > 0;
	}

	String processThirdBody(String mol) throws IOException {
		int pos = mol.indexOf('(');
		String label = mol.substring(0, pos).trim();

		ThirdBodyFlag = true;

		if (mol.toUpperCase().indexOf("(+M)") > 0) {
			ThirdBodyCoeffsFlag = true;

		} else {
			String thdbody = mol.substring(pos + 2, mol.length() - 1);
			thirdBodyMolecules = new ThirdBodyMolecules();
			String weight = "1.0";
			ThirdBodyWeight w = new ThirdBodyWeight(thdbody, weight);
			thirdBodyMolecules.put(label, w);
		}
		return label;
	}

	public String parseThirdBodyCoeffs() throws IOException {
		boolean done = false;
		String rxn = null;
		while (!done) {
			rxn = lines.nextNonBlank();
			if (rxn != null) {
				int pos = rxn.indexOf("!");
				String next;
				if (pos >= 0) {
					next = rxn.substring(0, pos);
					if (comment == null)
						comment = new String();
					comment += rxn.substring(pos) + "\n";
				} else
					next = rxn;

				String trimmed = next.trim();
				ChemkinCoefficients coefficients = new ChemkinCoefficients();
				coefficients.addCommentLine(comment);
				if (pos == 0) {

				} else {
					done = parseCoeffs(coefficients, trimmed);
				}
			} else {
				done = true;
			}
		}
		//System.out.println("==============================================================");
		//System.out.println(toString());
		return rxn;
	}

	boolean parseCoeffs(ChemkinCoefficients coefficients, String trimmed) throws IOException {
		boolean done = false;
		if (coefficients.parseReverse(trimmed)) {
			reverseCoefficients = coefficients;
		} else if (coefficients.parseLow(trimmed)) {
			lowCoefficients = coefficients;
		} else if (coefficients.parseHigh(trimmed)) {
			highCoefficients = coefficients;
		} else if (coefficients.parseTroe(trimmed)) {
			troeCoefficients = coefficients;
		} else if (coefficients.parseSRI(trimmed)) {
			sriCoefficients = coefficients;
		} else if (coefficients.parsePlog(trimmed)) {
			if (plogCoefficients == null) {
				plogCoefficients = new ArrayList<ChemkinCoefficients>();
			}
			plogCoefficients.add(coefficients);
		} else if (trimmed.indexOf("/") > 0) {
			thirdBodyMolecules = new ThirdBodyMolecules();
			thirdBodyMolecules.parse(trimmed);
		} else {
			done = true;
		}
		return done;
	}

	/*
	 * private String currentNonBlank(ChemkinString lines) { String next =
	 * lines.getCurrent(); if (next != null) { next = next.trim(); while
	 * (next.length() == 0) { next = lines.nextToken().trim(); } } return next;
	 * }
	 */
	/*
	 * private String nextNonBlank(ChemkinString lines) { lines.nextToken();
	 * return currentNonBlank(lines); }
	 */
	public String toString() {
		StringBuilder build = new StringBuilder();

		build.append("CHEMKIN REACTION\n");
		build.append("Comment: '" + comment + "'\n");

		build.append("Reactants as String: " + reactantsAsString + "\n");
		build.append("Products as String : " + productsAsString + "\n");
		build.append("Reactants:\n ");
		for (ChemkinMolecule mol : Reactants) {
			build.append(" ");
			build.append(mol.toString());
		}
		build.append("\n");

		build.append("Products: \n");
		for (ChemkinMolecule mol : Products) {
			build.append(" ");
			build.append(mol.toString());
		}
		build.append("\n");
		if (forwardCoefficients != null) {
			build.append(forwardCoefficients.toString());
			build.append("\n");
		}
		if (reversible)
			build.append("Reversible reaction\n");
		else
			build.append("Forward reaction\n");

		if (markedAsDuplicate)
			build.append("DUPLICATE\n");

		if (ThirdBodyCoeffsFlag)
			build.append("Third Body Coefficients\n");
		if (ThirdBodyFlag)
			build.append("Third Body Flag\n");

		if (reverseCoefficients != null) {
			build.append(reverseCoefficients);
			build.append("\n");
		}
		if (lowCoefficients != null) {
			build.append(lowCoefficients);
			build.append("\n");
		}
		if (troeCoefficients != null) {
			build.append(troeCoefficients);
			build.append("\n");
		}
		if (thirdBodyMolecules != null) {
			build.append(thirdBodyMolecules);
			build.append("\n");
		}
		if (plogCoefficients != null) {
			for (ChemkinCoefficients coefficients : plogCoefficients) {
				build.append(coefficients);
				build.append("\n");
			}
		}
		return build.toString();
	}

	/*
	 * If there are four tokens separated by spaces, assumming: 1. reactions
	 * (forward and reverse) 2-4 A, n, Ea If there are less than 4 means the
	 * declaration is not right
	 * 
	 * More than 4 means that the reaction has spaces
	 */
	public String repairReactionDeclaration(String line) throws IOException {
		StringBuilder builder = new StringBuilder();

		int pos = line.indexOf('!');
		String rxn = null;
		String comment = null;
		if (pos != -1) {
			rxn = line.substring(0, pos);
			comment = line.substring(pos);
		} else {
			rxn = line;
		}

		StringTokenizer tok = new StringTokenizer(rxn, " \t");
		if (tok.countTokens() == 4) {
			builder.append(line);
		} else if (tok.countTokens() < 4) {
			throw new IOException("Illegal Reaction declaration: '" + line + "' (" + line.length() + ")");
		} else {
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken().trim();
				if (beginOfCoefficients(token, tok.countTokens())) {
					if (tok.countTokens() == 2) {
						builder.append(" ");
						builder.append(token);
						builder.append(" ");
						builder.append(tok.nextToken());
						builder.append(" ");
						builder.append(tok.nextToken());
					} else {
						throw new IOException(
								"Illegal Reaction form (expected reaction followed by three coefficients:" + line);
					}
				} else {
					builder.append(token);
				}
			}
		}
		if (comment != null) {
			builder.append(" ");
			builder.append(comment);
		}
		return builder.toString();
	}

	private boolean beginOfCoefficients(String token, int tokencount) {
		boolean ans = false;
		if (recognizer.nonIntegerFloat(token))
			ans = true;
		else if (recognizer.stringIsAnInteger(token) && tokencount == 2)
			ans = true;
		return ans;
	}

	public ArrayList<ChemkinMolecule> getReactants() {
		return Reactants;
	}

	public void setReactants(ArrayList<ChemkinMolecule> reactants) {
		Reactants = reactants;
	}

	public ArrayList<ChemkinMolecule> getProducts() {
		return Products;
	}

	public void setProducts(ArrayList<ChemkinMolecule> products) {
		Products = products;
	}

	public boolean isThirdBodyFlag() {
		return ThirdBodyFlag;
	}

	public void setThirdBodyFlag(boolean thirdBodyFlag) {
		ThirdBodyFlag = thirdBodyFlag;
	}

	public boolean isThirdBodyCoeffsFlag() {
		return ThirdBodyCoeffsFlag;
	}

	public void setThirdBodyCoeffsFlag(boolean thirdBodyCoeffsFlag) {
		ThirdBodyCoeffsFlag = thirdBodyCoeffsFlag;
	}

	public boolean isReversible() {
		return reversible;
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isMarkedAsDuplicate() {
		return markedAsDuplicate;
	}

	public void setMarkedAsDuplicate(boolean markedAsDuplicate) {
		this.markedAsDuplicate = markedAsDuplicate;
	}

	public String getCommentChar() {
		return commentChar;
	}

	public void setCommentChar(String commentChar) {
		this.commentChar = commentChar;
	}

	public ChemkinString getLines() {
		return lines;
	}

	public void setLines(ChemkinString lines) {
		this.lines = lines;
	}

	public HashMap<String, ChemkinMolecule> getMolecules() {
		return molecules;
	}

	public void setMolecules(ChemkinMoleculeList molecules) {
		this.molecules = molecules;
	}

	public ChemkinCoefficients getForwardCoefficients() {
		return forwardCoefficients;
	}

	public ChemkinCoefficients getReverseCoefficients() {
		return reverseCoefficients;
	}

	public ChemkinCoefficients getLowCoefficients() {
		return lowCoefficients;
	}

	public ChemkinCoefficients getHighCoefficients() {
		return highCoefficients;
	}

	public ChemkinCoefficients getSriCoefficients() {
		return sriCoefficients;
	}

	public ChemkinCoefficients getTroeCoefficients() {
		return troeCoefficients;
	}

	public ArrayList<ChemkinCoefficients> getPlogCoefficients() {
		return plogCoefficients;
	}

	public ThirdBodyMolecules getThirdBodyMolecules() {
		return thirdBodyMolecules;
	}

	public boolean isHvLight() {
		return hvLight;
	}

	public boolean isHvLightAsReactant() {
		return hvLightAsReactant;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

}