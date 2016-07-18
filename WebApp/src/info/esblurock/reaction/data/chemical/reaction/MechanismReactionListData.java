package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class MechanismReactionListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	/** The nasa set. */
	@Persistent
	String mechanismKeyword;

	@Persistent
	Integer numberOfReactions;
	
	public MechanismReactionListData(String mechanismKeyword) {
		super();
		this.mechanismKeyword = mechanismKeyword;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}

	public int getNumberOfReactions() {
		return numberOfReactions.intValue();
	}
	public void setNumberOfReaction(int numberOfReactions) {
		this.numberOfReactions = new Integer(numberOfReactions);		
	}
}
