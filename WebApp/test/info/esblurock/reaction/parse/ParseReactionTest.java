package info.esblurock.reaction.parse;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.react.mechanisms.chemkin.ParseChemkinReaction;
import info.esblurock.reaction.data.chemical.reaction.CanonicalReactionName;
import info.esblurock.reaction.data.chemical.reaction.ParsedReactionInformation;

public class ParseReactionTest {

	@Test
	public void test() {

		String rxn11 = "H + o = oh ";
		normal(rxn11);

		String rxn1 = "h+o=oh";
		parse(rxn1);
		String rxn12 = "o+2h=h2o";
		parse(rxn12);
		String rxn2 = "h+o2(+m) = ho2(+m)";
		parse(rxn2);
		String rxn3 = "h2+m = h+h+m";
		parse(rxn3);
		String rxn4 = "ch3co(+m) = ch3+co(+m)";
		parse(rxn4);

		String mechname = "Mechanism";
		canonicalName(rxn11, null);
		canonicalName(rxn1, null);
		canonicalName(rxn1, mechname);
		canonicalName(rxn12, mechname);
		canonicalName(rxn12, mechname);
		
		String rxn5 = "LLNL#H2 ch3co(+m) = ch3+co(+m)  o+2h=h2o xxx o+h";
		total(rxn5);

		String rxn6 = "Standard#C2H4";
		parse(rxn6);

	}

	private void canonicalName(String rxnS, String mechname) {
		String name;
		try {
			CanonicalReactionName canonical = new CanonicalReactionName(mechname);
			name = canonical.getCanonicalName(rxnS, mechname, true);
			System.out.println("Query         : '" + rxnS + "'  (" + mechname + ")");
			System.out.println("Canonical name: '" + name + "'");
		} catch (IOException e) {
			fail(e.toString());
		}
	}

	private void normal(String rxnS) {
		System.out.println("------------------------------");
		System.out.println(rxnS);
		ParseChemkinReaction parse = new ParseChemkinReaction();
		String norm = parse.normalize(rxnS);
		System.out.println(norm);
	}

	private void parse(String rxnS) {
		System.out.println("------------------------------");
		System.out.println(rxnS);
		ParseChemkinReaction parse = new ParseChemkinReaction();
		try {
			parse.parse(rxnS, false);
			System.out.println("Reactants as String: " + parse.getReactantsAsString());
			System.out.println("Products as String : " + parse.getProductsAsString());
			System.out.println("Reactants          : " + parse.getReactants());
			System.out.println("Products           : " + parse.getProducts());
		} catch (IOException e) {
			fail(e.toString());
		}

	}
	private void total(String rxnS) {
		CanonicalReactionName canonical = new CanonicalReactionName();
		
		try {
			System.out.println("------------------------------");
			System.out.println("Original: " + rxnS);
			
			ParseChemkinReaction parse = new ParseChemkinReaction();
			String normed = parse.normalize(rxnS);
			System.out.println("Normalized: " + normed);
			
			ParsedReactionInformation parsed = canonical.getCanonicalReactionName(rxnS);
			System.out.println(parsed.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
