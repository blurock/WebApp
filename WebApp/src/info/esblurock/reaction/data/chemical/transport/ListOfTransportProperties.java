package info.esblurock.reaction.data.chemical.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.server.TextToDatabaseImpl;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;

public class ListOfTransportProperties extends ArrayList<SpeciesTransportProperty> {

	protected static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());

	String commentChar = "!";
	String comments;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListOfTransportProperties() {
		comments = "";
	}

	/**
	 * 
	 * @param tok
	 *            1 2 3 4 5 6 7
	 *            0123456789/123456789/123456789/123456789/123456789/123456789/
	 *            123456789/123456789/ AR 0 136.500 3.330 0.000 0.000 0.000
	 * 
	 *            (0,18): Species name (19,20): geometricIndex (20,30):
	 *            potentialWellDepth (30,40): collisionDiameter (40,50): dipole
	 *            (50,60): polarizability (60,70): collisionNumber
	 * @throws IOException
	 * 
	 */
	public void parse(String keyword, ChemkinStringFromStoredFile tok) throws IOException {
		String error = "";
		commentChar = tok.getCommentChar();
		String line = tok.getCurrent();
		tok.skipOverComments();
		boolean notdone = true;
		while (notdone) {
			line = tok.getCurrent();
			if (line != null) {
				line = line.trim();
				int pos = line.indexOf(commentChar);
				if (pos >= 0) {
					line = line.substring(0, pos);
				}
			}
			if (line == null) {
				notdone = false;
			} else if(line.toUpperCase().startsWith("END")) {
				notdone = false;
			} else if (line.length() >= 70) {
				String speciesName = line.substring(0, 19);
				String geometricIndex = line.substring(19, 20);
				String potentialWellDepth = line.substring(20, 30);
				String collisionDiameter = line.substring(30, 40);
				String dipole = line.substring(40, 50);
				String polarizability = line.substring(50, 60);
				String collisionNumber = line.substring(60, 70);

				SpeciesTransportProperty transport = new SpeciesTransportProperty(keyword, speciesName, geometricIndex,
						potentialWellDepth, collisionDiameter, dipole, polarizability, collisionNumber);
				this.add(transport);
				line = tok.nextToken();
			} else if(line.length() == 0) {
				line = tok.nextToken();
			} else {
				StringTokenizer linetok = new StringTokenizer(line, " ");
				if (linetok.countTokens() >= 7) {
					String speciesName = linetok.nextToken();
					String geometricIndex = linetok.nextToken();
					String potentialWellDepth = linetok.nextToken();
					String collisionDiameter = linetok.nextToken();
					String dipole = linetok.nextToken();
					String polarizability = linetok.nextToken();
					String collisionNumber = linetok.nextToken();
					SpeciesTransportProperty transport = new SpeciesTransportProperty(keyword, speciesName,
							geometricIndex, potentialWellDepth, collisionDiameter, dipole, polarizability,
							collisionNumber);
					this.add(transport);
					line = tok.nextToken();
				} else {
					error += "ERROR: '" + line + "'\n";
					line = tok.nextToken();
				}
			}
		}
		if (error.length() > 0) {
			throw new IOException("Error parsing Transport file: \n" + error);
		}
	}

}
