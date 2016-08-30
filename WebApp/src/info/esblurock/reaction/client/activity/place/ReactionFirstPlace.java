package info.esblurock.reaction.client.activity.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.Window;

public class ReactionFirstPlace extends Place {
	private String helloName;
	
	public ReactionFirstPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<ReactionFirstPlace>
	{

		@Override
		public String getToken(ReactionFirstPlace place) {
			return place.getHelloName();
		}

		@Override
		public ReactionFirstPlace getPlace(String token)  {
			return new ReactionFirstPlace(token);
		}

	}
}
