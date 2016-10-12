package info.esblurock.reaction.data.chemical.reaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class PhotoChemicalReaction extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	@Persistent
	String mechanismKeyword;
	@Persistent
	String reactionName;
	@Persistent
	Boolean asReactant;
	
	public PhotoChemicalReaction() {
	}
	public PhotoChemicalReaction(String mechanismKeyword, String reactionName, Boolean asReactant) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.reactionName = reactionName;
		this.asReactant = asReactant;
	}
	public String getMechanismKeyword() {
		return mechanismKeyword;
	}
	public String getReactionName() {
		return reactionName;
	}
	public Boolean getAsReactant() {
		return asReactant;
	}
}
