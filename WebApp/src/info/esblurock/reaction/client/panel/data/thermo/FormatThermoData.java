package info.esblurock.reaction.client.panel.data.thermo;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * The Class FormatThermoData.
 * 
 * Format lines of NASA polynomials 
 */
public class FormatThermoData {
	
	public FormatThermoData() {
	}

	/**
	 * Formated name.
	 *
	 * @param molname the molecule name
	 * @return the formatting string
	 */
	protected String formatedName(String molname) {
		return fillIn(24, molname, false);
	}

	/**
	 * Format atoms.
	 *
	 * @param atoms the atom names
	 * @param atomcnt the atom counts (corresponding to names)
	 * @return the formatted string
	 */
	String formatAtoms(String atoms[], int atomcnt[]) {
		StringBuilder buf = new StringBuilder();
		if (atoms != null) {
			for (int i = 0; i < 4; i++) {
				if (atoms[i] != null && atoms[i].length() > 0) {
					if(atoms[i].length() == 2) {
						buf.append(atoms[i].toLowerCase());
					} else if(atoms[i].length() == 1) {
						buf.append(atoms[i].toLowerCase() + " ");
					} else {
						buf.append("  ");
					}
					buf.append(fillIn(2,NumberFormat.getFormat("##").format(atomcnt[i]),false));
				} else {
					buf.append("     ");
				}
			}
		} else {
			buf.append("                    ");
		}
		return buf.toString();
	}

	/**
	 * Format temperature range.
	 *
	 * @param lowerT the lower t
	 * @param upperT the upper t
	 * @param middleT the middle t
	 * @return the string
	 */
	String formatTemperatureRange(double lowerT, double upperT, double middleT) {
		StringBuilder buf = new StringBuilder();
		buf.append("G");
		String l = fillIn(10,NumberFormat.getFormat("######.##").format(lowerT), true);
		String m = fillIn(10,NumberFormat.getFormat("######.##").format(middleT), true);
		String u = fillIn(10,NumberFormat.getFormat("######.##").format(upperT), true);
		buf.append(l);
		buf.append(m);
		buf.append(u);
		return buf.toString();
	}

	/**
	 * Format first line of a standard NASA polynomial.
	 *
	 * @param molname the molecule name
	 * @param atoms the atom names
	 * @param atomcnt the atom counts (corresponding to names)
	 * @param lowerT the lower temperature
	 * @param upperT the upper temperature
	 * @param middleT the middle temperature
	 * @return The first line
	 */
	String formatFirstLine(String molname, 
			String atoms[], int atomcnt[], 
			double lowerT, double upperT, double middleT) {
		String line = formatedName(molname) + 
				formatAtoms(atoms, atomcnt) + 
				formatTemperatureRange(lowerT, upperT, middleT) +
				"    1";
		return line;
	}
	
	String fillIn(int full, String object, boolean right) {
		int rest = full - object.length();
		String restS = "";
		for(int i=0;i<rest;i++) {
			restS += " ";
		}
		String ans;
		if(right) {
			ans = restS + object;
		} else {
			ans = object + restS;
		}
		return ans;
	}

	String formatScientific(double num) {
		String s = NumberFormat.getScientificFormat().overrideFractionDigits(12).format(num);
		return fillIn(15,s,true);
	}
	
	/**
	 * Format first line of a standard NASA polynomial.
	 *
	 * @param upper the upper temperature coefficients
	 * @param lower the lower temperature coefficients
	 * @return The second line
	 */
	String formatSecondLine(double upper[], double lower[]) {
		StringBuilder buf = new StringBuilder();
		buf.append(formatScientific(upper[0]));
		buf.append(formatScientific(upper[1]));
		buf.append(formatScientific(upper[2]));
		buf.append(formatScientific(upper[3]));
		buf.append(formatScientific(upper[4]));
		buf.append("    2");
		return buf.toString();
	}

	/**
	 * Format third line of a standard NASA polynomial.
	 *
	 * @param upper the upper temperature coefficients
	 * @param lower the lower temperature coefficients
	 * @return The third line
	 */
	String formatThirdLine(double upper[], double lower[]) {
		StringBuilder buf = new StringBuilder();
		buf.append(formatScientific(upper[5]));
		buf.append(formatScientific(upper[6]));
		buf.append(formatScientific(lower[0]));
		buf.append(formatScientific(lower[1]));
		buf.append(formatScientific(lower[2]));
		buf.append("    3");
		return buf.toString();
	}

	/**
	 * Format fourth line of a standard NASA polynomial.
	 *
	 * @param upper the upper temperature coefficients
	 * @param lower the lower temperature coefficients
	 * @return The fourth line
	 */
	String formatFourthLine(double upper[], double lower[]) {
		StringBuilder buf = new StringBuilder();
		buf.append(formatScientific(lower[3]));
		buf.append(formatScientific(lower[4]));
		buf.append(formatScientific(lower[5]));
		buf.append(formatScientific(lower[6]));
		buf.append("                   4");
		return buf.toString();
	}
}
