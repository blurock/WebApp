package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;
import info.esblurock.react.mechanisms.chemkin.ReactionForwardReverseType;
import info.esblurock.react.mechanisms.chemkin.ChemkinReactionList;
import info.esblurock.react.mechanisms.chemkin.SetOfReactionForwardReverseTypes;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;

import org.junit.Test;

public class ChemkinReactionReadTest {

	@Test
	
	public void SetOfReactionForwardReverseTypesTest() {

		System.out.println("Start");

		ChemkinString reactionS = new ChemkinString(
				"co+ho2  =  co2+oh  3.010E+13  0.00  2.300E+04", "!");

		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();

		String reaction = "co+ho2  =  co2+oh  3.010E+13  0.00  2.300E+04";
		ReactionForwardReverseType type = types.findReactionType(reaction);
		System.out.println("Reactants: '" + types.getReactants() + "'");
		System.out.println("Products:  '" + types.getProducts() + "'");
		if (type.isReversible())
			System.out.println("Reversible");
		if (type.isForward())
			System.out.println("Forward");
		if (type.isReverse())
			System.out.println("Reverse");

		reaction = "co+ho2=co2+oh  3.010E+13  0.00  2.300E+04";
		type = types.findReactionType(reaction);
		types.findReactionType(reaction);
		System.out.println("Reactants: '" + types.getReactants() + "'");
		System.out.println("Products:  '" + types.getProducts() + "'");
		if (type.isReversible())
			System.out.println("Reversible");
		if (type.isForward())
			System.out.println("Forward");
		if (type.isReverse())
			System.out.println("Reverse");

		reaction = "co+ho2=>co2+oh  3.010E+13  0.00  2.300E+04";
		type = types.findReactionType(reaction);
		types.findReactionType(reaction);
		System.out.println("Reactants: '" + types.getReactants() + "'");
		System.out.println("Products:  '" + types.getProducts() + "'");
		if (type.isReversible())
			System.out.println("Reversible");
		if (type.isForward())
			System.out.println("Forward");
		if (type.isReverse())
			System.out.println("Reverse");

	}


}
