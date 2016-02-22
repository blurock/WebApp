package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculeListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	@Persistent
	ArrayList<MechanismMoleculeData> molecules;

	public MechanismMoleculeListData(ArrayList<MechanismMoleculeData> molecules) {
		super();
		System.out.println("MechanismMoleculeListData");
		System.out.println("MechanismMoleculeListData + " + molecules.size());
		System.out.println("MechanismMoleculeListData + " + molecules);
		this.molecules = molecules;
	}

	public ArrayList<MechanismMoleculeData> getMolecules() {
		return molecules;
	}
}
