package info.esblurock.reaction.parse.objects;

import com.google.appengine.api.datastore.Query.Filter;

/**
 *  This is the base class for query filters. It bascially sets up the
 * 	Filter class with the procedure: getFilter.
 * 
 * These filters are used in the classes which implement {@link Interpretation}
 * in conjunction with a {@link ParseQuery}
 *  
 * @author edwardblurock
 *
 */
public class ParseObject {
	public ParseObject() {
		super();
	}
	/** return the Filter to be used in the query
	 * 
	 * @param input The input parameter to be used in the Filter.
	 * @return The filter
	 */
	public Filter getFilter(String input) {
		return null;
	}
	/**
	 * A description of the filter generated by this class.
	 */
	public String toString() {
		return "empty parse";
	}
}
