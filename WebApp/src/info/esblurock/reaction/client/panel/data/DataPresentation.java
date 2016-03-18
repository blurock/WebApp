package info.esblurock.reaction.client.panel.data;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.i18n.client.NumberFormat;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.client.panel.data.reaction.FormatChemkinCoefficientsData;
import info.esblurock.reaction.client.panel.data.reaction.FormatChemkinReactionData;
import info.esblurock.reaction.client.panel.data.thermo.FormatNASAPolynomialData;
import info.esblurock.reaction.client.panel.data.thermo.NASAThermoPanel;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;

public enum DataPresentation {
	MechanismMoleculeData {

		@Override
		public String asOnLine(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;

			String out = molecule.getMoleculeName() + "(" + molecule.getMechanismKeyword() + ")";
			return out;
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;
			String out = molecule.getMoleculeName() + "(" + molecule.getMechanismKeyword() + ")";
			return out;
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) data;
			String description = asOnLine(data);
			String title = "MechanismMoleculeData";
			BaseDataPresentation present = new BaseDataPresentation(title, description);
			return present;
		}

	},
	ChemkinReactionData {

		@Override
		public String asOnLine(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;

			StringBuilder build = new StringBuilder();
			GenerateReactionKeywords gen = new GenerateReactionKeywords(null);
			build.append(gen.getReactionSimpleName(reaction));
			build.append("   Coefs: ");
			if (reaction.getForwardCoefficients() != null)
				build.append("forward ");
			if (reaction.getReverseCoefficients() != null)
				build.append("reverse ");
			if (reaction.getTroeCoefficients() != null)
				build.append("troe ");
			if (reaction.getThirdBodyMolecules() != null)
				build.append("3rd Body ");

			return build.toString();
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			StringBuilder build = new StringBuilder();
			GenerateReactionKeywords gen = new GenerateReactionKeywords(null);
			build.append(gen.getReactionSimpleName(reaction));

			if (reaction.getForwardCoefficients() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				build.append(coeffpresent.asOnLine(reaction.getForwardCoefficients()));
				build.append("\n");
			}
			if (reaction.getReverseCoefficients() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				build.append(coeffpresent.asOnLine(reaction.getReverseCoefficients()));			
				build.append("\n");
			}
				
			if (reaction.getTroeCoefficients() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				build.append(coeffpresent.asOnLine(reaction.getTroeCoefficients()));							
				build.append("\n");
			}
				
			if (reaction.getHighCoefficients() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				build.append(coeffpresent.asOnLine(reaction.getHighCoefficients()));							
				build.append("\n");
			}
			if (reaction.getSriCoefficients() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				build.append(coeffpresent.asOnLine(reaction.getSriCoefficients()));							
				build.append("\n");
			}
				
			if (reaction.getPlogCoefficients() != null) {
				
				
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				for(ChemkinCoefficientsData c: reaction.getPlogCoefficients()) {
					build.append("Coefficients:");
					build.append(coeffpresent.asOnLine(c));
					build.append("\n");
				}
											
			}
				
		/*
			if (reaction.getThirdBodyMolecules() != null) {
				build.append("Coefficients:");
				DataPresentation coeffpresent = DataPresentation.valueOf("ChemkinCoefficientsData");
				coeffpresent.asOnLine(reaction.getReverseCoefficients());				
			}
				*/

			return build.toString();
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			FormatChemkinReactionData format = new FormatChemkinReactionData(reaction);
			String description = asOnLine(data);
			String title = "ChemkinReactionData";
			BaseDataPresentation present = new BaseDataPresentation(title, description);
			present.getModalContent().add(format);
			return present;
		}

	},
	ChemkinCoefficientsData {

		@Override
		public String asOnLine(DatabaseObject data) {
			ChemkinCoefficientsData coeffs = (ChemkinCoefficientsData) data;
			StringBuilder buf = new StringBuilder();
			if(coeffs.forward) {
				buf.append("Forward: ");
			} else if(coeffs.reverse) {
				buf.append("Reverse: ");
			} else if(coeffs.low) {
				buf.append("Low    : ");
			} else if(coeffs.high) {
				buf.append("High   : ");
			} else if(coeffs.plog) {
				buf.append("PLOG   : ");
			} else if(coeffs.sri) {
				buf.append("SRI    : ");
			} else if(coeffs.troe) {
				buf.append("Troe   : ");
			}
			buf.append("A=" + coeffs.getA() + "  ");
			buf.append("n=" + coeffs.getN() + "  ");
			buf.append("Ea=" + coeffs.getEa() + "  ");
			
			if(coeffs.getCoeffs() != null) {
				buf.append("[");
				boolean first = true;
				for(String c : coeffs.getCoeffs()) {
					if(first) {
						buf.append(", ");
						first = false;
					}
					buf.append(c);
				}
				buf.append("]");
			}
			
			return buf.toString();
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			// TODO Auto-generated method stub
			return asOnLine(data);
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			ChemkinCoefficientsData coeffs = (ChemkinCoefficientsData) data;
			FormatChemkinCoefficientsData formatter = new FormatChemkinCoefficientsData(coeffs);
			String title = "ChemkinCoefficientsData";
			String description = "";
			BaseDataPresentation present = new BaseDataPresentation(title, description);
			present.getModalContent().add(formatter);
			return null;
		}
		
	},
	NASAPolynomialData {

		@Override
		public String asOnLine(DatabaseObject data) {
			String ans = "";
			if (data != null) {
				NASAPolynomialData nasa = (NASAPolynomialData) data;
				IsomerData isomer = nasa.getMoleculeComposition();
				ans += nasa.getMoleculeName();
				if (nasa.getStandardEnthalpy() != null) {
					ans += "  H(298,cal/mole): "
							+ NumberFormat.getFormat("#######.###").format(nasa.getStandardEnthalpy());
				}
				if (nasa.getStandardEnthalpy() != null)
					ans += "  S(298,cal/K mole): "
							+ NumberFormat.getFormat("#######.###").format(nasa.getStandardEntropy());
			}
			return ans;
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			NASAPolynomialData nasa = (NASAPolynomialData) data;

			FormatNASAPolynomialData formatter = new FormatNASAPolynomialData();
			formatter.convertNASAPolynomial(nasa);
			StringBuilder buf = new StringBuilder();
			buf.append(formatter.getLine1());
			buf.append("\n");
			buf.append(formatter.getLine2());
			buf.append("\n");
			buf.append(formatter.getLine3());
			buf.append("\n");
			buf.append(formatter.getLine4());

			return data.toString();
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			NASAPolynomialData nasa = (NASAPolynomialData) data;
			String title = "NASA Polyomial";
			BaseDataPresentation presentation = new BaseDataPresentation(title, nasa.getMoleculeName());
			FormatNASAPolynomialData formatter = new FormatNASAPolynomialData();
			formatter.convertNASAPolynomial(nasa);
			NASAThermoPanel panel = new NASAThermoPanel(nasa);
			presentation.getModalContent().add(panel);
			
			return presentation;
		}

	};

	public String getMoleculeListString(ArrayList<String> mol) {
		Collections.sort(mol);
		StringBuilder build = new StringBuilder();
		for (String name : mol) {
			build.append(name);
			build.append("+");
		}
		String appendname = build.toString();
		String endmol = appendname.substring(0, appendname.length() - 2);
		return endmol;
	}

	public MaterialRow addRow(BaseDataPresentation presentation, String line) {
		MaterialRow row = new MaterialRow();
		MaterialColumn col = new MaterialColumn(line);
		row.add(col);
		presentation.getModalContent().add(row);
		return row;
	}

	abstract public String asOnLine(DatabaseObject data);

	abstract public String multiLineString(DatabaseObject data);

	abstract public BaseDataPresentation asDisplayObject(DatabaseObject data);
}
