package info.esblurock.reaction.data.chemical.molecule.isomer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import thermo.data.benson.NASAPolynomial;

public class CreateIsomerData {
	static String carbonS = "c";
	static String hydrogenS = "h";
	static String oxygenS = "o";
	static String nitrogenS = "n";

	public CreateIsomerData() {
	}

	public IsomerData create(NASAPolynomial nasa) {
		HashMap<String, Integer> atomcount = new HashMap<String, Integer>();
		for (int i = 0; i < 4; i++) {
			String atomS = nasa.atoms[i].toLowerCase().trim();
			int atomcnt = nasa.atomcnt[i];
			if (atomcnt != 0) {
				atomcount.put(atomS, atomcnt);
			}
		}
		IsomerData isomer = new IsomerData(atomcount);
		return isomer;
	}

	static public String standardIsomerName(IsomerData isomer) {
		Integer cI = isomer.getAtomCount(carbonS);
		Integer nI = isomer.getAtomCount(nitrogenS);
		Integer oI = isomer.getAtomCount(oxygenS);
		Integer hI = isomer.getAtomCount(hydrogenS);

		StringBuilder build = new StringBuilder();
		if (cI != null) {
			build.append(carbonS);
			if (cI.intValue() > 1)
				build.append(cI);
		}
		if (nI != null) {
			build.append(nitrogenS);
			if (nI.intValue() > 1)
				build.append(nI);
		}
		if (oI != null) {
			build.append(oxygenS);
			if (oI.intValue() > 1)
				build.append(oI);
		}
		if (hI != null) {
			build.append(hydrogenS);
			if (hI.intValue() > 1)
				build.append(hI);
		}
		String restname = restAtomString(isomer);
		build.append(restname);

		return build.toString();
	}

	static private String restAtomString(IsomerData isomer) {
		Set<String> set = isomer.getAtomCounts().keySet();
		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);
		StringBuilder build = new StringBuilder();
		for (String atomname : list) {
			if (!(atomname.equals(carbonS) || atomname.equals(nitrogenS) || atomname.equals(oxygenS)
					|| atomname.equals(hydrogenS))) {
				Integer aI = isomer.getAtomCount(atomname);
				build.append(atomname);
				if(aI.intValue() > 1)
					build.append(aI.toString());
			}
		}
		return build.toString();
	}

}
