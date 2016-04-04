package info.esblurock.reaction.client.panel.data.thermo;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;

/**
 * The Class FormatNASAPolynomialData.
 */
public class FormatNASAPolynomialData extends FormatThermoData {

	public FormatNASAPolynomialData() {

	}

	/** The name of the molecule. */
	String name;

	/** The atom counts (corresponding to atom names) */
	int[] atmcnt;

	/** The atom names (corresponding to atom counts). */
	String[] atoms;

	/** The upper coefficients. */
	double[] upper;

	/** The lower coefficients. */
	double[] lower;

	/** The lower temperatuer. */
	double lowerT;

	/** The middle temperature. */
	double middleT;

	/** The upper temperature. */
	double upperT;

	/** The first line of standard NASA Polynomial. */
	String line1;

	/** The second line of standard NASA Polynomial. */
	String line2;

	/** The third line of standard NASA Polynomial. */
	String line3;

	/** The fourth line of standard NASA Polynomial. */
	String line4;

	/**
	 * Convert nasa polynomial to four standard String lines
	 *
	 * @param nasa
	 *            the nasa polynomial information
	 */
	public void convertNASAPolynomial(NASAPolynomialData nasa) {
		processNASAPolynomial(nasa);
		line1 = formatFirstLine(name, atoms, atmcnt, lowerT, upperT,
		middleT);
		line2 = formatSecondLine(upper,lower);
		line3 = formatThirdLine(upper,lower);
		line4 = formatFourthLine(upper,lower);
	}

	/**
	 * Process nasa polynomial to convert the {@link NASAPolynomialData}
	 *
	 * @param nasa
	 *            the nasa
	 */
	void processNASAPolynomial(NASAPolynomialData nasa) {
		convertIsomer(nasa.getMoleculeComposition());
		if (nasa.getMoleculeName() != null) {
			name = nasa.getMoleculeName();
		}
		upper = convertArray(nasa.getUpper());
		lower = convertArray(nasa.getLower());
		lowerT = 0.0;
		if(nasa.getLowerT() != null) {
			lowerT = nasa.getLowerT().doubleValue();
		}
		middleT = 0.0;
		if(nasa.getMiddleT() != null) {
			middleT = nasa.getMiddleT().doubleValue();
		}
		upperT = 0.0;
		if(nasa.getUpperT() != null) {
			upperT = nasa.getUpperT().doubleValue();
		}
	}

	/**
	 * Convert ArrayList<Double> to double[]
	 *
	 * @param coeffs
	 *            the coefficients ArrayList<Double>
	 * @return the double[] of coefficients
	 */
	double[] convertArray(ArrayList<Double> coeffs) {
		double[] c;
		if (coeffs != null) {
			c = new double[coeffs.size()];
			int count = 0;
			for (Double coeff : coeffs) {
				c[count++] = coeff;
			}
		} else {
			c = new double[7];
			for(int i=0; i< 6 ; i++) {
				c[i] = 0.0;
			}
		}
		return c;
	}

	/**
	 * Convert isomer.
	 *
	 * @param isomer
	 *            the isomer
	 */
	void convertIsomer(IsomerData isomer) {
		if (isomer != null) {
			Set<String> set = isomer.getAtomCounts().keySet();

			atmcnt = new int[set.size()];
			atoms = new String[set.size()];
			int count = 0;
			for (String atom : set) {
				Integer cntI = isomer.getAtomCount(atom);
				atmcnt[count] = cntI.intValue();
				atoms[count] = atom;
				count++;
			}
		} else {
			Window.alert("IsomerData: null");
			atmcnt = new int[4];
			atoms = new String[4];
			for(int i=0; i<4;i++) {
				atmcnt[i] = 0;
				atoms[i] = "";
			}
		}
	}

	
	/**
	 * The first line of standard NASA Polynomial
	 *
	 * @return The first line
	 */
	public String getLine1() {
		return line1;
	}

	/**
	 * The second line of standard NASA PolynomialGets the line2.
	 *
	 * @return The second line
	 */
	public String getLine2() {
		return line2;
	}

	/**
	 * The third line of standard NASA Polynomial
	 *
	 * @return The third line
	 */
	public String getLine3() {
		return line3;
	}

	/**
	 * The fourth line of standard NASA Polynomial
	 *
	 * @return The fourth line
	 */
	public String getLine4() {
		return line4;
	}

}
