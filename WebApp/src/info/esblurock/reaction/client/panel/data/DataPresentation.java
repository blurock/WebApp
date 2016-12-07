package info.esblurock.reaction.client.panel.data;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.client.panel.data.reaction.FormatChemkinCoefficientsData;
import info.esblurock.reaction.client.panel.data.reaction.ReactionDataPresentation;
import info.esblurock.reaction.client.panel.data.thermo.FormatNASAPolynomialData;
import info.esblurock.reaction.client.panel.data.thermo.NASAThermoPanel;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywordsServer;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.description.DataSetReference;

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
			BaseDataPresentation present = new BaseDataPresentation(title, description,molecule);
			return present;
		}

	},
	ChemkinReactionData {

		@Override
		public String asOnLine(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;

			StringBuilder build = new StringBuilder();
			GenerateReactionKeywordsServer gen = new GenerateReactionKeywordsServer(null);
			build.append(gen.getReactionSimpleName(reaction));
			build.append("   Coefs: ");
			if (reaction.forward)
				build.append("forward ");
			if (reaction.reverse)
				build.append("reverse ");
			if (reaction.troe)
				build.append("troe ");
			if (reaction.sri)
				build.append("SRI ");
			if (reaction.high)
				build.append("high ");
			if (reaction.low)
				build.append("low ");
			if (reaction.plog)
				build.append("PLOG ");
			if (reaction.getThirdBodyMoleculeLabels() != null)
				build.append("3rd Body ");

			return build.toString();
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			StringBuilder build = new StringBuilder();
			GenerateReactionKeywordsServer gen = new GenerateReactionKeywordsServer(null);
			build.append(gen.getReactionSimpleName(reaction));
			return build.toString();
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			ChemkinReactionData reaction = (ChemkinReactionData) data;
			String description = asOnLine(data);
			String title = reaction.getReactionName();
			BaseDataPresentation present = new BaseDataPresentation(title, description,reaction);
			ReactionDataPresentation reactionpanel = new ReactionDataPresentation(reaction);
			present.getModalContent().add(reactionpanel);
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
			BaseDataPresentation present = new BaseDataPresentation(title, description,coeffs);
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
			BaseDataPresentation presentation = new BaseDataPresentation(title, nasa.getMoleculeName(),nasa);
			FormatNASAPolynomialData formatter = new FormatNASAPolynomialData();
			formatter.convertNASAPolynomial(nasa);
			NASAThermoPanel panel = new NASAThermoPanel(nasa);
			presentation.getModalContent().add(panel);
			return presentation;
		}

	}, DataSetReference {

		@Override
		public String asOnLine(DatabaseObject data) {
			DataSetReference reference = (DataSetReference) data;
			return reference.getReferenceTitle();
		}

		@Override
		public String multiLineString(DatabaseObject data) {
			DataSetReference reference = (DataSetReference) data;
			StringBuilder build = new StringBuilder();
			build.append(reference.getReferenceTitle());
			build.append("\n");
			boolean first = true;
			for(String name : reference.getAuthors()) {
				if(first) {
					build.append(", ");
				} else {
					first = false;
				}
				build.append(name);
			}
			build.append(reference.getDOI());
			build.append("\n");
			build.append(reference.getDatasetKeyword());
			build.append("\n");
			build.append(reference.getReferenceString());
			build.append("\n");
			return null;
		}

		@Override
		public BaseDataPresentation asDisplayObject(DatabaseObject data) {
			DataSetReference reference = (DataSetReference) data;
			String description = asOnLine(data);
			String title = reference.getDatasetKeyword();
			BaseDataPresentation present = new BaseDataPresentation(title,description,reference);
			addRow(present,reference.getReferenceTitle());
			StringBuilder build = new StringBuilder();
			boolean first = true;
			for(String name : reference.getAuthors()) {
				if(first) {
					build.append(", ");
				} else {
					first = false;
				}
				build.append(name);
			}
			addRow(present,build.toString());
			addRow(present,reference.getDOI());
			addRow(present,reference.getReferenceString());
			return present;
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
		MaterialColumn col = new MaterialColumn();
		col.setTitle(line);
		row.add(col);
		presentation.getModalContent().add(row);
		return row;
	}
	abstract public String asOnLine(DatabaseObject data);

	abstract public String multiLineString(DatabaseObject data);

	abstract public BaseDataPresentation asDisplayObject(DatabaseObject data);
}
