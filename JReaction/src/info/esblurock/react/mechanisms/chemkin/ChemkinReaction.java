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
	private ThirdBodyMolecules thirdBodyMolecules;

	private boolean ThirdBodyFlag = false;
	private boolean ThirdBodyCoeffsFlag = false;
	private boolean reversible = false;
	private String comment = null;
	private boolean markedAsDuplicate = false;
	private String commentChar = "!";

	private String reactantsAsString;
	private String productsAsString;

	ChemkinString lines;
	HashMap<String,ChemkinMolecule> molecules;
	
	PatternRecognizer recognizer = new PatternRecognizer();

	/** Creates a new instance of ChemkinReaction */
	public ChemkinReaction(ChemkinString ls, HashMap<String,ChemkinMolecule> mols) {
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
		thirdBodyMolecules = null;
	}

	public String parse() throws IOException {
		init();
		String current = lines.getCurrent();
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
			//reactantsAsString = types.getReactants();
			parseReactants(types.getReactants());
			StringTokenizer tok = parseProducts(types.getProducts());
			forwardCoefficients = new ChemkinCoefficients();
			forwardCoefficients.parseCoeffs(tok);
			reversible = rtype.isReverse();
			
			if (ThirdBodyFlag) {
				next = parseThirdBodyCoeffs();
			} else if(lines.nextToken() != null){
				ChemkinCoefficients reverse = new ChemkinCoefficients();
				if(reverse.parseReverse(lines.getCurrent())) {
					reverseCoefficients = reverse;
					lines.nextToken();
				}
			}
		} catch (StringIndexOutOfBoundsException io) {
			throw new IOException("StringIndexOutOfBoundsException: " + next);
		}
		return lines.getCurrent();
	}

	private void parseReactants(String react) throws IOException {
		System.out.println("Reactants: " + react);
		parseMolList(react, true);
	}

	private StringTokenizer parseProducts(String react) throws IOException {
		System.out.println("Products: " + react);
		StringTokenizer tok;
		tok = parseMolList(react, false);
		return tok;
	}

	private StringTokenizer parseMolList(String mollist, boolean reactants)
			throws IOException {
		StringTokenizer tok = new StringTokenizer(mollist, " ");
		int num = tok.countTokens();
		if (!reactants)
			num = num - 3;
		while (num > 0) {
			String mols = tok.nextToken();
			if(reactants)
				reactantsAsString = mols;
			else
				productsAsString = mols;
			mols = mols.replaceAll("\\(\\+[mM]\\)", "+XXX");
			// System.out.println("Mols: " + mols);
			StringTokenizer tok1 = new StringTokenizer(mols, "+");
			while (tok1.hasMoreTokens()) {
				String molS = tok1.nextToken();
				// System.out.println("Molecule Token: " + mol);
				if (molS.equals("XXX")) {
					System.out.println("ThirdBody: XXXX");
					ThirdBodyCoeffsFlag = true;
					ThirdBodyFlag = true;
				} else if (molS.equals("M") || molS.equals("m")) {
					System.out.println("ThirdBody: M");
					ThirdBodyFlag = true;
				} else {
					int dupcnt = 1;
					ChemkinMolecule mol = new ChemkinMolecule(molS.trim());
					if(recognizer.numberFollowedByCharacter(molS)) {
						int pos = recognizer.positionOfFirstLetter(molS);
						
						mol = new ChemkinMolecule(molS.substring(pos).trim());
						String dupS = molS.substring(0,pos);
						Integer dupI = new Integer(dupS);
						dupcnt = dupI.intValue();
						System.out.println("Count: " + dupcnt + "  Molecule: '" + mol.getLabel());
					}
					System.out.println("Molecule: '" + mol.getLabel() + "'");
					if (!molecules.containsKey(mol.getLabel())) {
						System.out
								.println("'" + mol.getLabel() + "' not there =============");
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
							throw new IOException("Species Name Error: '" + mol.getLabel() + "'");
						}
					}
					System.out.println("Dup: " + dupcnt);
					while (dupcnt > 0) {
						System.out.println("Add: '" + mol + "'");

						if (reactants)
							Reactants.add(mol);
						else
							Products.add(mol);
						dupcnt--;
					}
				}
			}
			num--;
		}
		System.out.println("Parse Mol done");
		return tok;
	}

	public String parseThirdBodyCoeffs() throws IOException {
		boolean done = false;
		String rxn = null;
		while (!done) {
			rxn = lines.nextToken();
			System.out.println("Next line: " + rxn);
			if (rxn != null) {
				int pos = rxn.indexOf("!");
				String next;
				if (pos > 0)
					next = rxn.substring(0, pos);
				else
					next = rxn;

				String trimmed = next.trim();
				ChemkinCoefficients coefficients = new ChemkinCoefficients();
				if (coefficients.parseReverse(trimmed)) {
					reverseCoefficients = coefficients;
				} else if (coefficients.parseLow(trimmed)) {
					lowCoefficients = coefficients;
				} else if (coefficients.parseTroe(trimmed)) {
					troeCoefficients = coefficients;
				} else if (next.indexOf("/") > 0) {
					thirdBodyMolecules = new ThirdBodyMolecules();
					thirdBodyMolecules.parse(next);
				} else {
					done = true;
				}
			} else {
				done = true;
			}
		}
		return rxn;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();

		build.append("CHEMKIN REACTION\n");
		build.append("Comment: '" + comment + "'\n");

		build.append("Reactants as String: " + reactantsAsString + "\n");
		build.append("Products as String : " + productsAsString + "\n");
		build.append("Reactants: ");
		for (ChemkinMolecule mol : Reactants) {
			build.append(" ");
			build.append(mol.toString());
		}
		build.append("\n");

		build.append("Products: ");
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

		return build.toString();
	}
/*
 *   If there are four tokens separated by spaces, assumming:
 *   	1. reactions (forward and reverse)
 *   	2-4  A, n, Ea
 *   If there are less than 4 means the declaration is not right
 *   
 *   More than 4 means that the reaction has spaces
 */
	public String repairReactionDeclaration(String line) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		int pos = line.indexOf('!');
		String rxn = null;
		String comment = null;
		if(pos != -1) {
			rxn = line.substring(0, pos);
			comment = line.substring(pos);
			System.out.println("Rxn: '" + rxn);
			System.out.println("Com: '" + comment);
		} else {
			rxn = line;
		}
		
		
		StringTokenizer tok = new StringTokenizer(rxn," ");
		if(tok.countTokens() == 4) {
			builder.append(line);
		} else if(tok.countTokens() < 4){
			throw new IOException("Illegal Reaction declaration: " + line);
		} else {
			PatternRecognizer ntests = new PatternRecognizer();
			while(tok.hasMoreTokens()) {
				String token = tok.nextToken();
				if(ntests.nonIntegerFloat(token)) {
					if(tok.countTokens() == 2) {
						builder.append(" ");
						builder.append(token);
						builder.append(" ");
						builder.append(tok.nextToken());
						builder.append(" ");
						builder.append(tok.nextToken());
					} else {
						throw new IOException("Illegal Reaction form (expected reaction followed by three coefficients:"
								+ line);
					}
				} else {
					builder.append(token);
				}
			}
		}
		if(comment != null) {
			builder.append(" ");
			builder.append(comment);
		}
		return builder.toString();
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

	public HashMap<String,ChemkinMolecule> getMolecules() {
		return molecules;
	}

	public void setMolecules(HashMap<String,ChemkinMolecule> molecules) {
		this.molecules = molecules;
	}

}