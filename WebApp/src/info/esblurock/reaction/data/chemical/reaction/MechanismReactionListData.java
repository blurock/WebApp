package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismReactionListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	@Persistent
	@Element(dependent = "true")
	ArrayList<ChemkinReactionData> reactionSet;
	
	public MechanismReactionListData(ArrayList<ChemkinReactionData> reactionSet) {
		this.reactionSet = reactionSet;
	}

}
