package info.esblurock.reaction.parse.objects.two;

public class SubjectAndPredicateParseObject extends TwoParameterParseObject {

	public SubjectAndPredicateParseObject() {
		super("subject","predicate");
	}
	/**
	 * A description of the filter generated by this class.
	 */
	public String toString() {
		return "object=input1 and predicate=input2";
	}

}
