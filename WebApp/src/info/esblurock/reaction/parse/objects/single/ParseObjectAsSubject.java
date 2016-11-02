package info.esblurock.reaction.parse.objects.single;


/** This sets up a filter to test whether 
 * the RDF 'subject' is equal to the input string
 * @author edwardblurock
 *
 */
public class ParseObjectAsSubject extends ParseObjectAsParameter {
	public ParseObjectAsSubject() {
		reference = "subject";
	}
}
