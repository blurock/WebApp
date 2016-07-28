package info.esblurock.reaction.server.datastore;

public class RDFDataKey extends DataPropertyValue {
	public static String RDF ="RDFKey";
	public static String subjectKey ="subject";
	public static String predicateKey ="predicate";
	public static String objectKey ="object";
	
	private String subject;
	private String predicate;
	private String object;
	
	public RDFDataKey() {
		super(RDF);
		subject = null;
		predicate = null;
		object = null;
	}
	
	public RDFDataKey(String subject, String predicate, String object) {
		super(RDF);
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
		this.addProperty(subjectKey, subject);
		this.addProperty(predicateKey, predicate);
		this.addProperty(objectKey, object);
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
