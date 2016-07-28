package info.esblurock.reaction.data.rdf;

import info.esblurock.reaction.data.DatabaseObject;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable

public class KeywordRDF extends DatabaseObject {
	
	public static String subjectProp = "subject";
	public static String predicateProp = "predicate";
	public static String objectProp = "object";

    @Persistent
    String subject;

    @Persistent
    String predicate;

    @Persistent
    String object;

    @Persistent
    String user;

    @Persistent
    String sourceCode;

    
    public KeywordRDF() {
    	
    }
 
	public KeywordRDF(String subject, String predicate, String object, String user, String sourceCode) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.user = user;
		this.sourceCode = sourceCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}


}