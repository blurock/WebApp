package info.esblurock.reaction.data.keyword;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class KeywordStandardization {

	@Persistent
	String standardKeyword;
	
	@Persistent
	String synonym;

	
	public KeywordStandardization() {
		super();
	}

	public KeywordStandardization(String standardKeyword, String synonym) {
		super();
		this.standardKeyword = standardKeyword;
		this.synonym = synonym;
	}

	public String getStandardKeyword() {
		return standardKeyword;
	}

	public String getSynonym() {
		return synonym;
	}

	
	
}
