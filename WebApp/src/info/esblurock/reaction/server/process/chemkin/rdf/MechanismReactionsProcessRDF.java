package info.esblurock.reaction.server.process.chemkin.rdf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyMoleculesData;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyWeightsData;
import info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismReactionsRDFTransaction;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;

public class MechanismReactionsProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;
	MechanismReactionsToDatabaseTransaction rxntransaction;
	MechanismReactionsRDFTransaction rdfTransaction;

	static public String isAProduct  = "isAsProduct";
	static public String isAReactant = "isAsReactant";
	static public String reactionS = "isReaction";
	static public String isMechanismReaction = "isMechanismReaction";
	static public String hasCoefficientS = "hasCoefficients";
	final static String mechanismReaction = "MechanismReaction";

	final String reactionThirdBodyMolelculeS = "ThirdBodyMolecule";
	
	static final public String forward = "Forward";
	static final public String reverse = "Reverse";
	static final public String troe = "Troe";
	static final public String low = "LowPressure";
	static final public String high = "HighPressure";
	static final public String plog = "PLOG";
	static final public String sri = "SRI";
	static final public String coefficientsS = "Coefficients";
	

	/**
	 * The reaction keyword generator. From the reaction generate a unique
	 * keyword (including the keywordBase -- which basically is the mechanism
	 * name)
	 */
	GenerateReactionKeywords generateReactions;
	StoreObject store;
	
	public MechanismReactionsProcessRDF() {
		super();
	}

	public MechanismReactionsProcessRDF(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismReactionsRDFTransaction";
	}

	@Override
	protected String getProcessName() {
		return "MechanismReactionsProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Store RDFs for mechanism reactions";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(toDatabaseS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(rdfTransactionS);
		return output;
	}

	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		rdfTransaction = new MechanismReactionsRDFTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(rdfTransaction);
	}
	@Override
	protected void createObjects() throws IOException {
		store = new StoreObject(user,keyword, outputSourceCode);
		List<DatabaseObject> reactionlist = ChemicalMechanismDataQuery.reactionsFromMechanismName(keyword);
		generateReactions = new GenerateReactionKeywords(keyword);
		for(DatabaseObject object : reactionlist) {
			ChemkinReactionData data = (ChemkinReactionData) object;
			String fullrxnS = data.getReactionName();
			String rxnS = generateReactions.getReactionSimpleName(data);

			store.storeObjectRDF(fullrxnS, data);
			store.setKeyword(fullrxnS);
			store.storeStringRDF(isMechanismReaction, rxnS);
			store.setKeyword(keyword);
			store.storeStringRDF(mechanismReaction,fullrxnS);

			store.setKeyword(fullrxnS);
			for(String name : data.getReactantKeys()) {
				store.setKeyword(fullrxnS);
				store.storeStringRDF(isAReactant,name);
			}
			for(String name : data.getProductKeys()) {
				store.setKeyword(fullrxnS);
				store.storeStringRDF(isAProduct,name);
			}
			if (data.getThirdBodyMolecules() != null) {
				storeThirdBodyRDF(data.getThirdBodyMolecules(),fullrxnS);
			}

			if (data.getForwardCoefficients() != null) {
				storeCoefficientData(data.getForwardCoefficients(),fullrxnS);
			}

			if (data.getReverseCoefficients() != null) {
				storeCoefficientData(data.getReverseCoefficients(),fullrxnS);
			}

			if (data.getLowCoefficients() != null) {
				storeCoefficientData(data.getLowCoefficients(),fullrxnS);
			}

			if (data.getHighCoefficients() != null) {
				storeCoefficientData(data.getHighCoefficients(),fullrxnS);
			}

			if (data.getTroeCoefficients() != null) {
				storeCoefficientData(data.getTroeCoefficients(),fullrxnS);
			}

			if (data.getSriCoefficients() != null) {
				storeCoefficientData(data.getSriCoefficients(),fullrxnS);
			}
			if (data.getPlogCoefficients() != null) {
				for(ChemkinCoefficientsData plogC : data.getPlogCoefficients()) {
					storeCoefficientData(plogC,rxnS);
				}
			}
		}
		store.flushStore();
		rdfTransaction.setRdfCount(store.getRdfCount());
	}
	
	
	protected void storeThirdBodyRDF(ThirdBodyMoleculesData data, String rxnkeyword) {
		StringBuilder build = new StringBuilder();
		build.append("[");
		for(ThirdBodyWeightsData weights : data.getThirdBodyMoleculeKeys()) {
			build.append(weights.getMolecule());
			build.append(" ");
		}
		build.append("]");
		store.setKeyword(rxnkeyword);
		store.storeStringRDF(reactionThirdBodyMolelculeS,build.toString());
	}

	private String stringCoefficients(ChemkinCoefficientsData data) {
		DecimalFormat formatterExp  = new DecimalFormat("0.####E00");
		double A = Double.parseDouble(data.getA());
		double n = Double.parseDouble(data.getN());
		double Ea = Double.parseDouble(data.getEa());
		DecimalFormat formatterSimp  = new DecimalFormat("#");
		formatterSimp.setMaximumFractionDigits(3);
		String AS = formatterExp.format(A);
		String nS = formatterSimp.format(n);
		String EaS = formatterExp.format(Ea);
		String coeffS = 
				"A=" + AS +
				" n="+ nS +
				" Ea=" + EaS;
		return coeffS;
	}

	private String stringConstants(ChemkinCoefficientsData data) {
		DecimalFormat formatterExp  = new DecimalFormat("0.####E00");
		StringBuilder build = new StringBuilder();
		for(String coef : data.getCoeffs()) {
			double c = Double.parseDouble(coef);
			String cS = formatterExp.format(c);
			build.append(cS);
			build.append(" ");
		}
		String constants = build.toString();
		return constants;
	}

	protected void storeCoefficientData(ChemkinCoefficientsData data, String rxnkeyword) {
		if(data.forward) {
			String forwardS = rxnkeyword + ":" + forward;
			String coeffS = stringCoefficients(data);
			String f = forward + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,forwardS);
			store.setKeyword(forwardS);
			store.storeStringRDF(f,coeffS);
		} else if(data.reverse) {
			String reverseS = rxnkeyword + ":" + reverse;
			String coeffS = stringCoefficients(data);
			String r = reverse + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,reverseS);
			store.setKeyword(reverseS);
			store.storeStringRDF(r,coeffS);
		} else if(data.low) {
			String lowS = rxnkeyword + ":" + low;
			String coeffS = stringCoefficients(data);
			String l = low + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,lowS);
			store.setKeyword(lowS);
			store.storeStringRDF(l,coeffS);
		} else if(data.high) {
			String highS = rxnkeyword + ":" + high;
			String coeffS = stringCoefficients(data);
			String h = high + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,highS);
			store.setKeyword(highS);
			store.storeStringRDF(h,coeffS);
		} else if(data.sri) {
			String sriS = rxnkeyword + ":" + sri;
			String constants = stringConstants(data);
			String s = sri + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,sriS);
			store.setKeyword(sriS);
			store.storeStringRDF(s,constants);
		} else if(data.troe) {
			String troeS = rxnkeyword + ":" + troe;
			String constants = stringConstants(data);
			String t = troe + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,troeS);
			store.setKeyword(troeS);
			store.storeStringRDF(t,constants);
		} else if(data.plog) {
			String plogS = rxnkeyword + ":" + plog;
			String constants = stringConstants(data);
			String p = plog + coefficientsS;
			store.setKeyword(rxnkeyword);
			store.storeStringRDF(hasCoefficientS,plogS);
			store.setKeyword(plogS);
			store.storeStringRDF(p,constants);
		} else {
			System.out.println("No RDF made for coefficient data: " + data.getKey());
		}
	}
	
}
