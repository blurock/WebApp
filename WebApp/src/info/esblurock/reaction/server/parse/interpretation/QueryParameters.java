package info.esblurock.reaction.server.parse.interpretation;

import java.io.Serializable;

public class QueryParameters implements Serializable {

	private static final long serialVersionUID = 1L;

	static public int standardLimit = 50;
	
	private String inputString;
	private int entityLimit;
	
	public QueryParameters(String inputString) {
		super();
		this.inputString = inputString;
		this.entityLimit = QueryParameters.standardLimit;
	}
	public QueryParameters(String inputString, int entityLimit) {
		super();
		this.inputString = inputString;
		this.entityLimit = entityLimit;
	}

	public int getEntityLimit() {
		return entityLimit;
	}

	public void setEntityLimit(int entityLimit) {
		this.entityLimit = entityLimit;
	}

	public String getInputString() {
		return inputString;
	}
	
	public void setInputString(String inputString) {
		this.inputString = inputString;
	}
	public String toString() {
		return "'" + inputString + "': " + Integer.toString(entityLimit);  
	}
	
}
