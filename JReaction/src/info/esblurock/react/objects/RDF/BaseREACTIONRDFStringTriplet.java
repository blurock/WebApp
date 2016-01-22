package info.esblurock.react.objects.RDF;

public class BaseREACTIONRDFStringTriplet {
	private String subjectS;
	private String predicateS;
	private String objectS;
	public BaseREACTIONRDFStringTriplet(String subjectS, String predicateS,
			String objectS) {
		this.subjectS = subjectS;
		this.predicateS = predicateS;
		this.objectS = objectS;
	}
	public String getSubjectS() {
		return subjectS;
	}
	public String getPredicateS() {
		return predicateS;
	}
	public String getObjectS() {
		return objectS;
	}
	
	

}
