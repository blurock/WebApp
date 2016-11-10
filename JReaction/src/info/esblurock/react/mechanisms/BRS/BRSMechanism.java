package info.esblurock.react.mechanisms.BRS;

//import se.lth.forbrf.terminus.utilities.ErrorFrame;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.SDF.SDFMolecule;
import info.esblurock.react.mechanisms.ReactionMechanism;

import java.util.*;
import java.io.*;

/**
 *
 * @author debug.reaction
 * @version
 */
public class BRSMechanism extends ReactionMechanism {
	public void readFromFile(File chosenFile) throws IOException {
		readMolecules(chosenFile);
		try {
			StringBuffer sb = new StringBuffer(4096);
			BufferedReader br = new BufferedReader(new FileReader(chosenFile));
			while (br.ready())
				sb.append(br.readLine() + System.getProperty("line.separator"));

			String data = sb.toString();
			data = data.replaceAll("%[^\\n]*\\n", ""); // process comments
			data = data.replaceAll("\\\\[^\\n]*\\n", ""); // process
															// concatenations
															// '\'
			// System.out.println(data);
			parse(data.getBytes());
		} catch (FileNotFoundException io) {
			ReactionLog.logError("Mech File not Found" + chosenFile.toString());
		} catch (IOException io) {
			ReactionLog.logError("I/O exception: " + io.toString());
		} catch (java.text.ParseException io) {
			ReactionLog.logError("Error in parsing file: " + io.toString());
		}
	}

	public void readMolecules(File mechfile) throws IOException {
		String name = mechfile.getName();
		String sdfS = name.replaceAll(".mech$", ".sdf");
		StringBuffer sb = new StringBuffer(4096);
		try {
			File mechfileF = new File(mechfile.getParent(), sdfS);
			BufferedReader br = new BufferedReader(new FileReader(mechfileF));
			while (br.ready())
				sb.append(br.readLine() + "\n");
			readMolecules(sb.toString());
		} catch (FileNotFoundException io) {
			throw new IOException("Molecule File not Found: ");
		}
	}

	public void readMolecules(String moleculesS) throws IOException {
		try {
			String[] molecules = moleculesS.split("\\$+|M  END");
			ReactionLog.logInfo("(M  END) Number of molecules: " + molecules.length);
			boolean noseed = true;
			for (int i = 0; i < molecules.length; i++) {
				String mol = molecules[i];
				if (mol.trim().length() > 0) {
					SDFMolecule m = new SDFMolecule();
					m.parse(mol.getBytes());

					if (noseed) {
						seedMolecule = m.getMoleculeName();
						noseed = false;
						ReactionLog.logInfo("Read in Molecule Seed: " + seedMolecule);
					}
					Molecules.put(m.getMoleculeName(), m);
				}
			}
		} catch (java.text.ParseException io) {
			throw new IOException("Error in parsing file: " + io.toString());
		} catch (Exception ex) {
			throw new IOException(ex.toString());
		}
	}

	public void parse(byte[] data) throws java.text.ParseException {
		parseMechanism(new String(data));
	}

	public void parseMechanism(String lines) throws java.text.ParseException {
		boolean success = true;
		rxnClasses = new Vector();
		StringTokenizer elements = new StringTokenizer(lines, "\n");

		String classcoeffs = elements.nextToken();
		while (!classcoeffs.startsWith("CLASSCOEFFICIENTS") && elements.hasMoreTokens())
			classcoeffs = elements.nextToken();

		if (classcoeffs.startsWith("CLASSCOEFFICIENTS")) {
			try {
				boolean notdone = true;
				while (notdone && success) {
					String line = elements.nextToken();
					if (line.startsWith("END"))
						notdone = false;
					else {
						BRSMechanismRxnClass rxnclass = new BRSMechanismRxnClass();
						success = rxnclass.parseCoeffs(line);
						rxnClasses.add(rxnclass);
					}
				}
			} catch (Exception ios) {
				ReactionLog.logError("Error in parsing ReactionMechanism Coefficients: " + ios.toString());
			}
			try {
				String classequivs = elements.nextToken();
				String equivsend = elements.nextToken();
				if (classequivs.startsWith("CLASSEQUIV") && equivsend.startsWith("END")) {
					for (int i = 0; i < rxnClasses.size() && success; i++) {
						String res = "";
						String line;

						do {
							line = elements.nextToken();
							res += line + "\n";
						} while (!line.startsWith("END"));
						BRSMechanismRxnClass rxnclass = (BRSMechanismRxnClass) rxnClasses.elementAt(i);
						rxnclass.parse(res.getBytes());
					}
				} else {
					success = false;
				}
			} catch (NoSuchElementException ios) {
				// throw new java.text.ParseException(ios, -1);
			}
		} else {
			ReactionLog.logError("CLASSCOEFFICIENTS not found");
		}

	}

	public boolean parse(String name) {
		try {
			parse(name.getBytes());
		} catch (java.text.ParseException e) {
			return false;
		}

		return true;
	}
}