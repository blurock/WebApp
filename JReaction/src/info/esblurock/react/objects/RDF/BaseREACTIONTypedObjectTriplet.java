package info.esblurock.react.objects.RDF;

import info.esblurock.react.objects.BaseREACTIONBaseObject;

public class BaseREACTIONTypedObjectTriplet extends BaseREACTIONRDFStringTriplet {
	
	private BaseREACTIONBaseObject object;

	public BaseREACTIONTypedObjectTriplet(String subjectS, String predicateS,
			BaseREACTIONBaseObject object) {
		super(subjectS, predicateS, object.getClass().getCanonicalName());
		this.object = object;
	}

	public BaseREACTIONBaseObject getObject() {
		return object;
	}
	
}
