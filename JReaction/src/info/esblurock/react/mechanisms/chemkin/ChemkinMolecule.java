package info.esblurock.react.mechanisms.chemkin;

/**
 *
 * @author  reaction
 */
public class ChemkinMolecule {
    String label;
    /** Creates a new instance of ChemkinMolecule */
    public ChemkinMolecule() {
    	label = "";
    }
    public ChemkinMolecule(String text) {
    	label = text;
    }
    public String getLabel() {
    	return label;
    }
    public String toString() {
    	return label;
    }
	public boolean equals(Object ob1) {
		ChemkinMolecule o1 = (ChemkinMolecule) ob1;
		boolean ret = false;
		//ChemkinMolecule mol = (ChemkinMolecule) o;
		if(o1.getLabel().equalsIgnoreCase(this.getLabel()))
			ret = true;
		return ret;
	}
}
