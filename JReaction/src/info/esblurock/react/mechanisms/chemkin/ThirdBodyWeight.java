package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;

public class ThirdBodyWeight {
	private ChemkinMolecule molecule;
	private double weight;
	public ThirdBodyWeight(ChemkinMolecule molecule, double weight) {
		this.molecule = molecule;
		this.weight = weight;
	}
	public ThirdBodyWeight(String moleculeS, String weightS) throws IOException{
		molecule = new ChemkinMolecule(moleculeS);
		try {
		Double weightD = new Double(weightS);
		weight = weightD.doubleValue();
		} catch(NumberFormatException ex) {
			throw new IOException("Third Body weight error: " + weightS);
		}
	}
	public ChemkinMolecule getMolecule() {
		return molecule;
	}
	public double getWeight() {
		return weight;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append(molecule.getLabel());
		build.append('/');
		build.append(weight);
		build.append('/');
		
		return build.toString();
	}
}
