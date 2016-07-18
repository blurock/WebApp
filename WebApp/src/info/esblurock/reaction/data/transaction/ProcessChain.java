package info.esblurock.reaction.data.transaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ProcessChain extends DatabaseObject {

    /** The keyword string representing the object being stored. */
    @Persistent
    String keyword;
 
    /** The input transaction object class name (as a string)**/
    @Persistent
    String transactionObjectTypeInput;
    
    /** The input source code input */
    @Persistent
    String sourceCodeInput;

    /** The output transaction object class name (as a string)**/
    @Persistent
    String transactionObjectTypeOutput;
    
    /** The output source code input */
    @Persistent
    String sourceCodeOutput;

    

	public ProcessChain() {
	}



	public ProcessChain(String keyword, String transactionObjectTypeInput, String sourceCodeInput,
			String transactionObjectTypeOutput, String sourceCodeOutput) {
		this.keyword = keyword;
		this.transactionObjectTypeInput = transactionObjectTypeInput;
		this.sourceCodeInput = sourceCodeInput;
		this.transactionObjectTypeOutput = transactionObjectTypeOutput;
		this.sourceCodeOutput = sourceCodeOutput;
	}



	public String getKeyword() {
		return keyword;
	}



	public String getTransactionObjectTypeInput() {
		return transactionObjectTypeInput;
	}



	public String getSourceCodeInput() {
		return sourceCodeInput;
	}



	public String getTransactionObjectTypeOutput() {
		return transactionObjectTypeOutput;
	}



	public String getSourceCodeOutput() {
		return sourceCodeOutput;
	}
}
