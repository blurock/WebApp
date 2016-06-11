package info.esblurock.reaction.data.transaction.chemkin;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismReactionsToDatabaseTransaction  extends DatabaseObject {
 
	@Persistent
    Integer reactionCount;

    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;
  
	public MechanismReactionsToDatabaseTransaction(String user, String fileCode, String keyWord) {
		super();
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
	}

	public Integer getReactionCount() {
		return reactionCount;
	}

	public void setReactionCount(Integer reactionCount) {
		this.reactionCount = reactionCount;
	}
    
    
 
}
