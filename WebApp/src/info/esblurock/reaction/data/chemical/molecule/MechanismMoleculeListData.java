package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculeListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	@Persistent
	@Element(dependent = "true")
	ArrayList<MechanismMoleculeData> molecules;

	public MechanismMoleculeListData(ArrayList<MechanismMoleculeData> molecules) {
		super();
		this.molecules = molecules;
	}

	public ArrayList<MechanismMoleculeData> getMolecules() {
		return molecules;
	}
}
