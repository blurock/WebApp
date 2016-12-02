package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class MoleculeStructureInformation  extends DatabaseObject {
    @Persistent
    ArrayList<String> names;
    
    public MoleculeStructureInformation(String dataSetKeyword, String moleculeName) {
   }

    public void addName(String name) {
    	names.add(name);
    }
    
    
}
