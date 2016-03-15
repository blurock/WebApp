package info.esblurock.reaction.data.chemical.mechanism;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;

/**
 * The Class ChemicalMechanismData which stores all the information from a CHEMKIN mech file
 */
@PersistenceCapable
public class ChemicalMechanismData extends DatabaseObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/** The element list. */
	@Persistent(dependent="true")
	ChemicalElementListData  elementList;
	
	/** The molecule list. */
	@Persistent(dependent="true")
	MechanismMoleculeListData  moleculeList;
	
	/** The reaction list. */
	@Persistent(dependent="true")
	MechanismReactionListData reactionList;

	
	public ChemicalMechanismData() {
		super();
	}
	/**
	 * Instantiates a new chemical mechanism data.
	 *
	 * @param elementList the element list
	 * @param moleculeList the molecule list
	 * @param reactionList the reaction list
	 */
	public ChemicalMechanismData(ChemicalElementListData elementList, MechanismMoleculeListData moleculeList,
			MechanismReactionListData reactionList) {
		super();
		this.elementList = elementList;
		this.moleculeList = moleculeList;
		this.reactionList = reactionList;
	}

	/**
	 * Gets the element list.
	 *
	 * @return the element list
	 */
	public ChemicalElementListData getElementList() {
		return elementList;
	}

	/**
	 * Gets the molecule list.
	 *
	 * @return the molecule list
	 */
	public MechanismMoleculeListData getMoleculeList() {
		return moleculeList;
	}

	/**
	 * Gets the reaction list.
	 *
	 * @return the reaction list
	 */
	public MechanismReactionListData getReactionList() {
		return reactionList;
	}

	
}
