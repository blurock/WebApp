package info.esblurock.reaction.data.transaction;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class SetOfTransactionKeys  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	String key;

    @Persistent
    ArrayList<String> rdfKeyWords;

    SetOfTransactionKeys() {
    }
    
    SetOfTransactionKeys(ArrayList<String> rdfKeyWords) {
    	this.rdfKeyWords = rdfKeyWords;
    }
    
	public String getKey() {
		return key;
	}
	
	public ArrayList<String> getRdfKeyWords() {
		return rdfKeyWords;
	}
}
