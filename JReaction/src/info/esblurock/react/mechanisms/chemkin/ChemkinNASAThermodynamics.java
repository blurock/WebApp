package info.esblurock.react.mechanisms.chemkin;

import info.esblurock.info.react.data.molecules.isomers.Isomer;

import java.io.IOException;
import java.util.StringTokenizer;

import thermo.data.benson.SetOfNASAPolynomials;
import thermo.data.benson.NASAPolynomial;

public class ChemkinNASAThermodynamics {
	
	private String commentString;
	private String thermoKeywordS;
	private String endKeywordS;
	
	private SetOfNASAPolynomials NASAthermo;
	private String comment;
	
	private Double lowTemperature;
	private Double middleTemperature;
	private Double highTemperature;
	
	public void init() {
		commentString = "!";
		thermoKeywordS = "THERMO";
		endKeywordS = "END";
	}
	
	public ChemkinNASAThermodynamics() {
		init();
	}

	public void parse(ChemkinString lines) throws IOException {
		System.out.println("Parse THERMO");
		NASAthermo = new SetOfNASAPolynomials();
		lines.setTrim(false);
		skipOverComments(lines);
		
		if(compareKeyword(lines.getCurrent(), thermoKeywordS)) {
			parseFirstTemperatureLine(lines);
			lines.nextToken();
			skipOverComments(lines);
			
			boolean notdone = true;
			int count = 0;
			while(notdone) {
				String first = lines.getCurrent();
				if(compareKeyword(first,endKeywordS)) {
					notdone = false;
				} else {
					try {
					NASAPolynomial nasa = parseNASAPolynomial(lines);
					
					Isomer isomer = new Isomer(nasa);
					NASAthermo.add(nasa);
					} catch(IOException ex) {
						System.out.println("Thermo not added: \n" + first);
					}
				}
				lines.nextToken();
			}
		} else {
			throw new IOException("Expected: '" + thermoKeywordS + "' got " + lines.getCurrent());
		}
	}
	public boolean compareKeyword(String line, String keyword) {
		String l = line.trim().toUpperCase();
		return l.startsWith(keyword);
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append(comment);
		build.append("\n");
		build.append(thermoKeywordS);
		for(NASAPolynomial nasa : NASAthermo) {
			build.append(nasa.toString());
			//build.append("\n");
		}
		build.append(endKeywordS);
		build.append("\n");
		
		return build.toString();
	}
	
	private void skipOverComments(ChemkinString lines) {
		commentString = lines.getCommentChar();
		boolean nomorecomments = true;
		StringBuilder buildcomment = new StringBuilder();
		while(nomorecomments) {
			String comment = lines.getCurrent().trim();
			if(comment.startsWith(commentString)) {
				buildcomment.append(lines.getCurrent());
				buildcomment.append("\n");
				lines.nextToken();
			} else {
				nomorecomments = false;
			}
		}
		comment = buildcomment.toString();
	}
	private void parseFirstTemperatureLine(ChemkinString lines) throws IOException {
		StringTokenizer temps = new StringTokenizer(lines.nextToken());
		if(temps.countTokens() == 3) {
			String lowTemperatureS = temps.nextToken();
			String middleTemperatureS = temps.nextToken();
			String highTemperatureS = temps.nextToken();
			try {
			lowTemperature = new Double(lowTemperatureS);
			middleTemperature = new Double(middleTemperatureS);
			highTemperature = new Double(highTemperatureS);
			} catch(NumberFormatException ex) {
				throw new IOException("Expected three temperatures, got '" + lines.getCurrent() + "'");
			}
		}
		
	}
	private NASAPolynomial parseNASAPolynomial(ChemkinString lines) throws IOException {
		String line1 = lines.getCurrent();
		String line2 = lines.nextToken();
		String line3 = lines.nextToken();
		String line4 = lines.nextToken();
		
		NASAPolynomial nasa = new NASAPolynomial();
		nasa.parse(line1,line2,line3,line4);
		return nasa;
	}

	public Double getLowTemperature() {
		return lowTemperature;
	}

	public Double getMiddleTemperature() {
		return middleTemperature;
	}

	public Double getHighTemperature() {
		return highTemperature;
	}

	public void setCommentString(String commentString) {
		this.commentString = commentString;
	}

	public void setThermoKeywordS(String thermoKeywordS) {
		this.thermoKeywordS = thermoKeywordS;
	}

	public void setEndKeywordS(String endKeywordS) {
		this.endKeywordS = endKeywordS;
	}

	public SetOfNASAPolynomials getNASAthermo() {
		return NASAthermo;
	}

}
