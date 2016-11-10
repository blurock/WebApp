package info.esblurock.reaction.data.reaction;

import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.react.mechanisms.chemkin.ChemkinString;

/*
====== Molecule Name File: mol/molsdf/C0HO-names.lst
hydrogen-radical
- AlternativeNames CHEMKIN H
- AlternativeNames Short H
hydrogen
- AlternativeNames CHEMKIN H2
- AlternativeNames Short H2

 */
public class AlternativeMolecules {
	static public String alternative = "- AlternativeNames";

	ArrayList<MoleculeNameCorrespondence> corrset;
	String title;

	public AlternativeMolecules() {
		corrset = new ArrayList<MoleculeNameCorrespondence>();
	}

	public void parse(ChemkinString chemkintokenizers) {
		title = chemkintokenizers.nextNonBlank();
		String line = chemkintokenizers.nextNonBlank();
		while (line != null) {
			String moleculename = line;
			line = chemkintokenizers.nextNonBlank();
			boolean moleculeblock = true;
			while (moleculeblock) {
				if (line == null) {
					moleculeblock = false;
				} else if (line.trim().startsWith(alternative)) {
					String trimmed = line.trim().substring(alternative.length());
					StringTokenizer tok = new StringTokenizer(trimmed);
					if (tok.countTokens() >= 2) {
						String type = tok.nextToken();
						String substitutename = tok.nextToken();
						MoleculeNameCorrespondence corrs = new MoleculeNameCorrespondence(moleculename, type,
								substitutename);
						corrset.add(corrs);
					}
				} else {
					moleculeblock = false;
				}
			}
		}
	}
}
