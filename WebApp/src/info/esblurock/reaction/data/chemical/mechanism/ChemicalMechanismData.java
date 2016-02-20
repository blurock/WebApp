package info.esblurock.reaction.data.chemical.mechanism;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;

@PersistenceCapable
public class ChemicalMechanismData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	@Persistent(dependent = "true")
	ChemicalElementListData  elementList;
	
	@Persistent(dependent = "true")
	MechanismMoleculeListData  moleculeList;
	
	@Persistent(dependent = "true")
	MechanismReactionListData reactionList;

	public ChemicalMechanismData(ChemicalElementListData elementList, MechanismMoleculeListData moleculeList,
			MechanismReactionListData reactionList) {
		super();
		this.elementList = elementList;
		this.moleculeList = moleculeList;
		this.reactionList = reactionList;
	}

	public ChemicalElementListData getElementList() {
		return elementList;
	}

	public MechanismMoleculeListData getMoleculeList() {
		return moleculeList;
	}

	public MechanismReactionListData getReactionList() {
		return reactionList;
	}

	
}
