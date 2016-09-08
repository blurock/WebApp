package info.esblurock.reaction.client.activity.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReactionInformationPlace extends Place {
	private String helloName;
	
	public ReactionInformationPlace(String token) {
		this.helloName = token;
	}

	public String getHelloName() {
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<ReactionInformationPlace> {
		@Override
		public String getToken(ReactionInformationPlace place) {
			return place.getHelloName();
		}
		@Override
		public ReactionInformationPlace getPlace(String token)  {
			return new ReactionInformationPlace(token);
		}
	}
	
}
