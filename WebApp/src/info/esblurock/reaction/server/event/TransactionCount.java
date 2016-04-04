package info.esblurock.reaction.server.event;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class TransactionCount {
	
	@PrimaryKey
	@Persistent
	String userName;
	
	@Persistent
	Integer transactionCount;
	
	public TransactionCount(String userName) {
		this.userName = userName;
		this.transactionCount = new Integer(0);
	}
	public int incrementCount() {
		return ++transactionCount;
	}
}
