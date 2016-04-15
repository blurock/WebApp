package info.esblurock.info.react.data.molecules.isomers;

import thermo.data.benson.NASAPolynomial;
import thermo.data.structure.structure.AtomCount;
import thermo.data.structure.structure.AtomCounts;

public class Isomer {
	private AtomCounts counts;
	
	public Isomer(NASAPolynomial nasa) {
		int[] atomcounts = nasa.atomcnt;
		String[] atomicNames = nasa.atoms;
		String molecule = nasa.name;
		counts = new AtomCounts();
		int count = 0;
		while(count < 4) {
			if(atomcounts[count] > 0 ) {
			String name = atomicNames[count];
			counts.put(name, atomcounts[count]);
			counts.getAtomNames().add(name);
			}
			count++;
		}
	}
	public Isomer(AtomCounts counts) {
		this.counts = counts;
	}
	
	public String getName() {
		return counts.isomerName();
	}
}
